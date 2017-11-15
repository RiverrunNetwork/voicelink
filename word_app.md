
# 应用指令词

- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信
- 第二步 将你自定义的词组通过如下方式传给大耳朵，比如你做了一款 播放器类型的app,您希望在整个应用中只要用户说 “搜索” 都能跳转到搜索界面
  那么你就将如下代码添加到application里面 例如：
 ```java
            val tell = Tell()
            val hashMap = HashMap<String, String>()
            hashMap.put("!搜索", "searchTag")
            tell.appCacheMap = hashMap
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
 ```
- 第三步 注册service 步骤和 “自定义语音界面 第二步” 步骤相同 当用户命中我们会回调onInterception(...) 方法

- 第四步 解释Key和Value的作用 hashMap.put("你好", "缓存");

