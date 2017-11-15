
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
 其中 “搜索” 会和用户说的话进行模糊匹配 如果用户说“xx搜索xx” 依然会命中，如果在“搜索”前面添加“!” 例如"!搜索" 则会进行精确匹配，
 如果命中那么 大耳朵会返回给你 "searchTag" 这块你可以写任何值 这就需要小伙伴的发挥了！<br>

