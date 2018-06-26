# **1、前言：**
现在市面上APP的框架有mvc、mvp、mvvm等，每一种框架都有利弊，Google推荐的框架是Mvvm+LiveData+Room，这样好像能解决很多问题，我用了之后的感觉还不错，所以打算写个文档，大家共同学习。

## **1.1、该框架能解决的问题**
- 数据缓存，在无网环境下本地缓存有数据就加载本地数据
- 加载速度，首先加载本地数据，同时再请求网络，成功后替换数据，速度杠杠的
- 系统稳定，利用Google组件让App更稳定
- 项目分层，分层后使每个模块独立，逻辑更清晰
- 谁用谁知道

# **2、LiveData的介绍:**

LiveData是一个可以被观察的数据持有类，它可以感知并遵循Activity、Fragment或Service等组件的生命周期，只有在组件出于激活状态（STARTED、RESUMED）才会通知观察者有数据更新。example:

### **2.1、ViewModel**:
```
public class NameViewModel extends ViewModel {

    private MutableLiveData<String> mCurrentName;

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<String>();
        }
        return mCurrentName;
    }
}
```



### **2.2、Activity**:
```
public class NameActivity extends AppCompatActivity {

    private NameViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取NameViewModel
        mModel = ViewModelProviders.of(this).get(NameViewModel.class);

        //创建一个观察者更新UI
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                //接收到了newName
                mNameTextView.setText(newName);
            }
        };

        //在activity里观察LiveData的改变
        mModel.getCurrentName().observe(this, nameObserver);

        //为了更直观，直接在这里发送数据，一般发送数据是在网络请求完成火车数据库查询完成后更新UI
        mButton.setOnClickListener(new OnClickListener() {
              @Override
             public void onClick(View v) {
                  //给一个newName给观察者
                  //setValue在主线程使用 postValue在子线程使用
                  String newName = "John Doe";
                  mModel.getCurrentName().setValue(newName);
        }
      });
    }
    //LiveData变型
    //将对象User变为String
    LiveData<User> userLiveData = ...;
    LiveData<String> userName = Transformations.map(userLiveData, user -> {
    user.name + " " + user.sex  });

    //将String变为User
    private LiveData<User> getUser(String id) {
     ...;
    }
    LiveData<String> userId = ...;
    LiveData<User> user = Transformations.switchMap(userId, id -> getUser(id) );

```
**2.3、扩展MediatorLiveData**

LiveData的子类，它可以合并多个LiveData,当任意一个LiveData被触发，它监听的对象就会改变，例如：

如果我们需要一个LiveData同时监听网络和数据库的变化，达到更新UI的目的，我们就可以用MediatorLiveData。一个LiveData与数据库关联，一个LiveData与网络请求关联。

# **3、项目架构图:**

<img src="https://github.com/lingdianguole/AppAchitecture/blob/master/help/final-architecture.png"/>

- **仓库层Repository连接ViewModel和数据（本地数据、网络数据）**
- **LiveData在ViewModel里定义，Activity/Fragment里观察，网络请求和数据库查询的结果都是LiveData,目的是为了当数据变化时传递数据，自动更新UI**
- **Room持久化数据组件**
- **Retrofit网络请求框架**

### 3.1、关键类**NetworkBoundResource.java**解析
它是个抽象类，同时也是一个公共类，对于每一次获取数据，针对请求对象ResultType和返回对象RequestType，用MediatorLiveData查询本地数据库和请求网络数据。
```
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource() {
        result.setValue(Resource.loading(null));   //初始化空数据，开始loading
        LiveData<ResultType> dbSource = loadFromDb(); //从本地数据库查询数据
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);   //移除一个观察者
            if (shouldFetch(data)) {         //判断是否请求网络，如果是则发起网络请求，否则把本地数据给观察者，提示加载成功
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));  //setvalue一次代表通知观察者一次，会收到提示
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData))); //把本地数据给观察者
        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());   //存储网络返回结果
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData))); //返回错误信息
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(RequestType response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);  //存储数据到本地
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData))); //再从本地查询数据
            }
        }.execute();
    }

     //存储网络访问结果到数据库
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    //是否请求网络
    @MainThread
    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }

    //从数据库取数据
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    //发起网络请求
    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    //网络请求失败
    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
```
如果有本地数据，先加载本地数据渲染出来，同时请求网络，请求成功后插入数据库，替换掉之前的数据，再查询出来渲染页面，完成加载，流程如下:

<img src="https://github.com/lingdianguole/AppAchitecture/blob/master/help/network-bound-resource.png"/>


#### 3.1、仓库层调用
  ```
   public LiveData<Resource<List<JokeEntity>>> loadPopularJokes(String type, String page) {
        return new NetworkBoundResource<List<JokeEntity>, JokeResponse>() {

            @Override
            protected void saveCallResult(@NonNull JokeResponse item) {
                    jokeDao.saveJokes(item.getData());
                }
            }

            @NonNull
            @Override
            protected LiveData<List<JokeEntity>> loadFromDb() {
                return jokeDao.loadJokes(type);
            }

            @NonNull
            @Override
            protected Call<JokeResponse> createCall() {
                return jokeDBService.loadJokes(type, page);
            }
        }.getAsLiveData();
    }
```
#### 3.2、ViewModel调用仓库层
```
public class JokeListViewModel extends ViewModel {
    private JokeRepository jokeRepository;
    private MutableLiveData<String> pageLiveData = new MutableLiveData<>();
    public final LiveData<Resource<List<JokeEntity>>> jokeLiveData =
            Transformations.switchMap(pageLiveData, type -> jokeRepository.loadPopularJokes(type, "1"));

    @Inject
    public JokeListViewModel(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public void loadJokes(String type) {
        pageLiveData.setValue(type); //将type传递给jokeLiveData
    }
}

```
#### 3.3、Activity观察数据变化
//得到数据listReource，更新UI
```
viewModel.getPopularMovies().observe
(this, listResource -> dataBinding.setResource(listResource));
```
#### 3.4、效果



# **4、总结:**
以上就是关键代码，重点是要明白每一个组件的用法，然后把它们连在一起，理解项目结构图。有任何意见，欢迎指正，我的邮箱lingdianguole@l63.com
## [项目地址](https://github.com/lingdianguole/AppAchitecture)

参考文档：
https://developer.android.com/topic/libraries/architecture/guide.html#recommendedapparchitecture
https://github.com/googlesamples/android-architecture-components
