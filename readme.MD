1需要做的功能：添加汽车开启结束按钮
//开启汽车调用这些代码
         Intent intent = new Intent(this, MyService.class);
         intent.putExtra(MyService.type, 1);
         startService(intent);
//关闭汽车时调用这些代码
Intent intent = new Intent(this, MyService.class);
         intent.putExtra(MyService.type, 2);
         startService(intent);
//判断汽车状态的代码为
int p=SPUtils.getInt(this, SPUtils.IS_ON, 0);
p为1表明为开启状态
p为2表明为关闭状态
//获取汽车开始时间
long time = SPUtils.getLong(this, SPUtils.BEGIN_TIME, 0);
//读取开车记录,onFinish里的list为数据
 LitePal.order("duration").findAsync(CarOnBean.class).listen(new FindMultiCallback<CarOnBean>() {
           @Override
           public void onFinish(List<CarOnBean> list) {
           }
       });

        //数据详情的话应该是以开车时间段为区间记录数据
        //这个where里面的语句可能有问题，没有尝试
        LitePal.where("time<? and time>?",beginTime,endTime).order("duration").findAsync(MessageBean.class).listen(new FindMultiCallback<MessageBean>() {
            @Override
            public void onFinish(List<MessageBean> list) {
            }
        });

