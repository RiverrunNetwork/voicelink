# 目前支持的指令
```java
查看大图| 商品大图
{
	"action" : "watch",
	"filter" : "bigPic"
}


查看详情 | 图文详情
{
	"action" : "watch",
	"filter" : "detail"
}

宝贝评价 | 查看评价 | 商品评价 |评价
{
	"action" : "watch",
	"filter" : "evaluate"
}

店铺首页 | 查看店铺首页 | 店铺页 
{
	"action" : "watch",
	"filter" : "homePage"
}

宝贝推荐 | 看看宝贝推荐 
{
	"action" : "watch",
	"filter" : "recommend"
}


上拉
{
	"action" : "move",
	"moveDir" : "up"
}


下拉
{
	"action" : "move",
	"moveDir" : "down"
}

确认下单，确认
{
	"action" : "sureBuy"
}

领优惠券 | 领券 | 领取优惠券
{
	"action" : "receive",
	"filter" : "coupons"
}


扫码购买
{
	"action" : "scanBuy"
}

立即购买
{
	"action" : "buyNow"
}

确定
{
	"action" : "clickOk"
}

添加第2个到购物车/加入购物车(position="")
{
	"action" : "add2ShoppingCart",
	"position" : "2"
}

图片切换
{
	"action" : "pic_change"
}

数量选择
{
	"action" : "amount_select"
}
```
