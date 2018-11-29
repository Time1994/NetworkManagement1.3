package com.eroadcar.networkmanagement.utils;

import java.util.regex.Pattern;

public class Constant {
    // 图片缓存
    public final static String IMAGE_CACHE = "image_cache";
    // 图片获取连接
    public final static String IMAGE_HTTP = "http://116.236.115.126:88/htoa/userImg/evcar/";
    // 正式地址
    public final static String HTTP = "http://116.236.115.124:9090/";// 测试
    // "http://116.236.115.125:9000/";//正式"http://116.236.115.124:9090/"
    // //
    // http://116.236.115.124:9090//http://192.168.168.103:8080/http://101.231.214.34:33231
    // 登录
    public final static String LOGIN = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Public/login";

    // 获取统计信息
    public final static String REQSTATISTICSFORMANAGER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqStatisticsForManager";

    // 获取身份类型
    public final static String GETROLES = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Public/getRoles";

    // 获取员工列表
    public final static String GETUSERS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/getUsers";

    // 添加员工
    public final static String ADDUSER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/addUser";

    // 分配权限
    public final static String DISTRIBUTEROLE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/distributeRole";

    // 获取统计信息，销售顾问
    public final static String REQSTATISTICSFORSELLER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqStatisticsForSeller";

    // 意见反馈
    public final static String SENDSUGGEST = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/sendSuggest";

    // 修改信息
    public final static String MODIFYUSER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/modifyUser";

    // 征信
    public final static String REQZXINFO = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/reqZxInfo";

    // 获取销售订单接口（管理者）
    public final static String GETSELLORDERFORMANAGER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getSellOrderForManager";

    // 获取销售订单接口（非管理者）
    public final static String GETSELLORDERFORSELLER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getSellOrderForSeller";

    // 获取客户与车辆信息（该接口考虑到后续扩展，个人信息与车辆分开存放，具体看返回数据）
    public final static String GETSELLCUSTOMER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getSellCustomer";

    // 选择车辆
    public final static String SELECTBUYCAR = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/selectBuyCar";

    // 完善信息上传图片
    public final static String UPLOADSELLPICS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/uploadSellPics";

    // 完善信息
    public final static String FILLSELLDATA = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/fillSellData";

    // 获取客户列表
    public final static String GETPOTENTIALCLIENTO = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getPotentialClient";
    // 新增客户
    public final static String NEWCUSTOMERO = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/PotentialClient/newCustomer";
    // 获取回访记录
    public final static String GETFOLLOWRECORDO = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getFollowRecord";
    // 租赁
    public final static String REQLEASECARS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/reqLeaseCars";

    // 租赁列表 员工
    public final static String GETLEASEORDERFORUSER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getLeaseOrderForUser";

    // 租赁列表 员工
    public final static String GETLEASEORDERFORMANAGER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getLeaseOrderForManager";

    // 获取客户与车辆信息（该接口考虑到后续扩展，个人信息与车辆分开存放，具体看返回数据）租赁
    public final static String GETLEASECUSTOMER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getLeaseCustomer";

    // 租赁 修改状态
    public final static String LEASEVERIFY = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/leaseVerify";

    //推送信息统计
    public final static String GETPUSHCENTER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Public/getPushCenter";
    //统计信息
    public final static String REQSTATISTICSBYMWD = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqStatisticsByMwd";
    //消息
    public final static String GETPUSHINFOFORSELF = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Public/getPushInfoForSelf";
    //车架号模糊查询
    public final static String GETVINBYKEY = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getVinByKey";
    //根据店铺获取人员
    public final static String GETWORKERSBYSHOP = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/getWorkersByShop";
    //获取人员详情
    public final static String GETWORKERSDETAIL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/getWorkersDetail";
    //获取任务列表
    public final static String GETJOBLISTDETAIL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/getJobListDetail";
    //发布任务
    public final static String DISTRIBUTEJOB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/distributeJob";
    //根据店铺及人员
    public final static String GETSHOPWORKERSDETAIL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/getShopWorkersDetail";

    //发布的任务
    public final static String GETMASTERJOBDETAIL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/getMasterJobDetail";

    //接收的任务
    public final static String GETSLAVEJOBDETAIL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/getSlaveJobDetail";

    //处理任务
    public final static String DEALWORKERJOB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/dealWorkerJob";
    //目标提交
    public final static String SUBMITWORKERINFO = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Worker/submitWorkerInfo";

    //统计信息个人
    public final static String REQSTATISTICSBYMWDFORUSER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqStatisticsByMwdForUser";





    // 验证码
    public final static String CODE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/public/verifyImg";
    // 系统注册接口
    public final static String SIGNUP = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/signUp";
    // 获取验证码接口
    public final static String GETVERIFICATECODE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getVerificateCode";
    // 非公司人员登录接口
    public final static String SIGNIN = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/signIn";
    // 销售提成列表
    public final static String GETSELLREWARD = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getSellReward";
    // 租赁提成列表
    public final static String GETLEASEREWARD = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getLeaseReward";
    // 接车接口
    public final static String ACCEPTCAR = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Jobs/acceptCar";
    // 车辆入库中获取车型数据接口
    public final static String GETJCCARTYPE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/getJcCarType";
    // 车辆入库中获取颜色数据接口
    public final static String JCGETCARCOLOR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/getCarColor";
    // 获取库存门店
    public final static String GETKUCUNSHOP = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/getKucunShop";
    // 获取充电桩列表
    public final static String GETCHARGEPILES = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/ChargeKucun/getChargePiles";
    // 添加库存车辆
    public final static String ADDKUNCUCAR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/addKuncuCar";
    // 根据库存code获取充电桩信息
    public final static String GETCHARGEBYKUCUNCODE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/getChargeByKucunCode";
    // 接车入库51项检查
    public final static String JCDEALQIRUI51CAR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/dealQirui51Car";
    // 获取充电桩厂商
    public final static String GETCHARGEFACTORY = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/getChargeFactory";
    // 库存车辆列表接口
    public final static String GETKUCUNCAR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/CarKucun/getKucunCar";
    // 获取车的库存列表
    public final static String GETCARKUCUNLISTBYCARTYPE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCarkucunListByCarType";

    // 类别接口
    public final static String GETGROUP = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/getGroup";
    // 标准项目接口
    public final static String GETYWITEMDB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/System/getywItemDb";
    // 项目接口
    public final static String GETITEMDATA = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Jobs/getItemData";
    // 材料接口
    public final static String GETMATERIALSSEL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Jobs/getMaterialsSel";
    // 已选材料返回接口
    public final static String GETMATERIAOUTDB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Jobs/getMateriaOutDb";
    // 项目分配接口
    public final static String DISTRIBUTEITEMS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Jobs/distributeItems";
    // 人员接口
    public final static String GETWORKERSDB = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getWorkersDb";
    // 材料选择接口,添加材料
    public final static String INSERTMATERIAOUT = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/InsertMateriaOut";
    // 核价接口
    public final static String CHECKPRICE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/checkPrice";
    // 删除项目接口
    public final static String DELPROGRAM = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/delProgram";
    // 删除材料接口.未测
    public final static String DELMATERIALITEM = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/delMaterialItem";
    // 保存工单接口
    public final static String SAVEORDERINFO = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/saveOrderInfo";
    // 获取工单项目接口（在厂车辆）
    public final static String GETINSHOPCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getInshopCars";
    // 获取工单分配接口
    public final static String GETDESTRIBUTE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getDestribute";
    // 领工完工接口
    public final static String GOOPERITEMS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/goOperItems";
    // 工单打印接口
    public final static String PRINTLIST = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/printList";
    // 根据车牌号获取车辆的信息
    public final static String GETCARDATA = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getCarData";
    // 根据车牌号获取订单号
    public final static String GETORDERDATA = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getOrderData";
    // 结算清单
    public final static String CHECKOUT = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/checkOut";
    // 添加项目
    public final static String INSERTITEMDB = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/insertItemDb";
    // 取消工单
    public final static String CANCELORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/cancelOrder";
    // 登出
    public final static String LOGOUT = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/public/logout";
    // 新建车辆
    public final static String NEWCAR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/newCar";
    // 获取已完成订单接口
    public final static String GETFINISHORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getFinishOrder";
    // 获取项目所派人员列表
    public final static String GETITEMWORKERS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getItemWorkers";
    // 减人接口
    public final static String DELPERSONS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/delPersons";
    // 加人接口
    public final static String ADDPERSONS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/addPersons";
    // 消息中心
    public final static String GETINFOCENTER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getInfoCenter";
    // 更新
    public final static String GETAPKINFO = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/ApkManager/getApkInfo";
    // 修改密码
    public final static String MODIFYPWD = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/modifyPassword";
    // 修改密码
    public final static String modifyPwd = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Public/modifyForMb";
    // 项目工时模糊查询
    public final static String GETSTANDARDITEMS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getStandardItems";
    // 车牌号模糊查询
    public final static String GETCARLICENSEBYLETTER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getCarlicenseByletter";
    // 在厂车辆筛选
    public final static String GETINSHOPCARBYGROUP = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getInshopCarByGroup";
    // 修改项目数量
    public final static String SAVEITEMNUM = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/saveItemNum";
    // 修改材料数量
    public final static String SAVEMATERIALNUM = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/saveMaterialNum";

    // 租车记录 +isboss
    public final static String GETLEASECARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getLeaseCars";

    // 销售车记录 +isboss
    public final static String GETSELLCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getSellCars";

    // 获取车型
    public final static String GETCARTYPE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCarType";

    // 获取车辆轨迹
    public final static String GETCARTRACK = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/External/getCarTrack";

    // 获取车辆位置
    public final static String GETCARPOSITION = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/External/getCarPosition";

    // 车架号模糊查询接口
    public final static String GETCARBYVIN = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getCarByVin";
    // 按车牌号模糊查询
    public final static String GETCARBYLICENSE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getCarByLicense";
    // 获取车的颜色
    public final static String GETCARCOLOR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCarColor";
    // 获取车的库存
    public final static String GETCARKUCUNNUMBYCARTYPE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCarkucunNumByCarType";
    // 获取中心网点
    public final static String GETCENTERPOINTS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCenterPoints";
    // 获取投保险种
    public final static String GETINSURANCETYPE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getInsuranceType";
    // 租赁订单详情
    public final static String GETLEASEORDERBYID = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getLeaseOrderbyId";
    // 销售订单详情
    public final static String GETSELLORDERBYID = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getSellOrderbyId";
    // 销售信息上传
    public final static String REQURESELLCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/requreSellCars";

    // 租赁信息上传
    public final static String REQURELEASECARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/requreLeaseCars";
    // 上传身份证
    public final static String UPLOADSELLSQPIC = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/uploadSellsqPic";
    // 上传驾驶证
    public final static String UPLOADLEASESQPIC = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/uploadLeasesqPic";
    // 租赁状态改变
    public final static String CHANGELEASECARSSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeLeaseCarsState";
    // 销售状态改变
    public final static String CHANGESELLCARSSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeSellCarsState";
    // 交车接口
    public final static String CHANGEJCCARSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeSellJcCarState";
    // 取消销售订单
    public final static String CANCELSELLORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/cancelSellOrder";
    // 取消租赁订单
    public final static String CANCELLEASEORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/cancelLeaseOrder";
    // 重新提交销售订单
    public final static String REQURERESELLCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/requreReSellCars";
    // 重新提交租赁订单
    public final static String REQURERELEASECARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/requreReLeaseCars";
    // 获取材料类型
    public final static String GETMATERIALGROUP = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getMaterialGroup";
    // 获取材料
    public final static String GETMATERIALSBYGROUP = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getMaterialsByGroup";
    // 获取配件目录材料
    public final static String GETMATERIALSDIRBYGROUP = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getMaterialsDirByGroup";
    // 确认配送材料
    public final static String SQMATERIALS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Store/sqMaterials";
    // 配送追踪
    public final static String SENDMATERIALTRACK = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Store/sendMaterialTrack";
    // 仓库配送搜索材料
    public final static String GETMATERIALSBYKEY = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getMaterialsByKey";
    // 仓库配送 配件目录搜索材料
    public final static String GETMATERIALSDIRBYKEY = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getMaterialsDirByKey";
    // 改变仓库配送状态
    public final static String CHANGEMATERIALSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeMaterialState";
    // 仓库配送详情
    public final static String GETMATERIALBYID = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getMaterialbyId";
    // 用户上下线
    public final static String USERONLINE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Users/userOnLine";
    // 用户上下线状态
    public final static String GETUSERONLINESTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Users/getUserOnLineState";
    // 公告列表
    public final static String GETANNOUNCEMENT = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/NoticeInfo/getAnnouncement";
    // 修改密码接口
    public final static String MODIFYPASSWORD = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/modifyPassword";
    // 紧急通知
    public final static String GETNOTICE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/NoticeInfo/getNotice";
    // 通讯录
    public final static String GETCONTACKBOOK = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/NoticeInfo/getContackBook";
    // 修改通知状态
    public final static String CHANGENOTICEREADSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/NoticeInfo/changeNoticeReadState";
    // 轮询通知状态
    public final static String GETUNREADNOTICE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/NoticeInfo/getUnreadNotice";
    // 上传交车图片
    public final static String UPLOADSELLCARPIC = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/uploadSellCarPic";
    // 车型数据获取
    public final static String GETALLCARTYPE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/CarKucun/getAllCarType";
    // 车型数据获取
    public final static String GETALLCARTYPESELL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/SellCar/getAllCarType";
    // 车型数据获取
    public final static String GETCARIMGBYTC = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/SellCar/getCarImgByTc";

    // APK下载地址
    // public final static String DOWNHTTP = HTTP
    // + "workdir/ernet/ernet/public/upload/apk/NetworkManagement.apk";
    // public final static String DOWNHTTP =
    // "【翼禄服务】专注于新能源汽车销售租赁、维修管理、仓库配送管理等服务应用APP,下载链接："
    // + "http://url.cn/2EI2KL9";
    public final static String DOWNHTTP = "http://www.eroadcar.com/web/html/mendianjiameng.html";
    // 新建车辆获取信息
    public final static String RNETCARINFO = "http://116.236.115.123:88/OAapp/WebObjects/OAapp.woa?mqApply=rNetCarInfo&plateNum=";
    // APP类型
    public final static String APPTYPE = "android";
    // 个人中心_销售人员
    public final static String SALESMAN_PESONALCENTER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getPersonalCenter";
    // 个人中心_维修人员
    public final static String SERVICEMAN_PESONALCENTER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getPersonalCenter";
    // 个人中心_管理人员 ===== isboss
    public final static String Gl_PESONALCENTER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getPersonalGlCenter";
    // 个人中心_老板信息
    public final static String GETSHOPINFO = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getShopInfo";
    // 个人中心_社会销售
    public final static String ALL_SALE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getPersonalCenter";
    // 社会销售APP车型数据
    public final static String GETCARTYPE_STRING = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getCarType";
    // 销售人员获取上下家信息接口
    public final static String GETRELATIONINFO = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getRelationInfo";
    // 历史记录
    public final static String GETHISTORYORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getHistoryOrder";
    // 历史记录详情
    public final static String GETSHISTORYORDERDETAIL = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/getsHistoryOrderDetail";
    // 收款确认
    public final static String SHOUKUANCONFIM = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Jobs/shouKuanConfim";

    // 路救列表
    public final static String GETLUJIULIST = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/Customers/getLujiuList";
    // 路救列表
    public final static String GETLUJIUJOBBYCODE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Customers/getLujiuJobBycode";
    // 根据车牌号获取相关路救信息
    public final static String GETCUSTOMCARBYLICENSE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Customers/getCustomCarByLicense";
    // 根据车牌号获取相关路救信息
    public final static String GETCARINFOFORLUJIU = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Customers/getCarInfoForLujiu";
    // 添加路救信息
    public final static String LUJIUCONFIRM = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Customers/luJiuConfirm";
    // 改变路救状态
    public final static String CHANGELUJIUSTATE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Customers/changeLujiuState";
    // 接受路救
    public final static String ACCEPTLUJIUOPER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Customers/acceptLujiuOper";
    // 获取省级信息
    public final static String GETPROVINCE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getProvince";
    // 获取市级信息
    public final static String GETCITY = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCity";
    // 获取县区信息
    public final static String GETCOUNTRY = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getCountry";
    // 销售添加车辆
    public final static String ADDBUYCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/addBuyCars";
    // 删除车辆
    public final static String DELBUYCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/delBuyCars";
    // 添加租赁车辆
    public final static String ADDLEASECARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/addLeaseCars";
    // 租赁订单列表
    public final static String GETLEASEORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getLeaseOrder";
    // 销售订单列表
    public final static String GETSELLORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getSellOrder";
    // 销售订单详情对应车辆列表
    public final static String GETBUYCARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getBuyCars";
    // 车辆51项检查
    public final static String QIRUI51CAR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/qirui51Car";
    // 提交51项检查
    public final static String DEALQIRUI51CAR = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/dealQirui51Car";
    // 销售交车接口
    public final static String CHANGESELLJCCARSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeSellJcCarState";
    // 租赁交车接口
    public final static String CHANGELEASEJCCARSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeLeaseJcCarState";

    // 提车接口
    public final static String CHANGETCCARSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/changeTcCarState";
    // 删除租赁车辆
    public final static String DELLEASECARS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/delLeaseCars";

    // 接车入库列表批次
    public final static String GETPRECARBYPICI = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PreCarAccept/getPreCarByPici";
    // 接车批量
    public final static String DEALPILIANGCONFIRM = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PreCarAccept/dealPiLiangConfirm";
    // 接车批量
    public final static String dealQirui51Car = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PreCarAccept/dealQirui51Car";
    // 获取批次列表
    public final static String GETKUCUNPICI = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PreCarAccept/getKucunPici";
    // 获取客户列表
    public final static String GETPOTENTIALCLIENT = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getPotentialClient";
    public final static String GETPOTENTIALCLIENTFORMANAGER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getPotentialClientForManager";
    public final static String GETPOTENTIALCLIENTFORUSER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getPotentialClientForUser";

    //销售订单修改状态
    public final static String ZXSELLVERIFY = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/YfBusiness/zxSellVerify";


    // 添加顾客信息
    public final static String NEWCUSTOMER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/newCustomer";
    // 获取潜客回访纪录
    public final static String GETFOLLOWRECORD = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getFollowRecord";
    // 添加客户回访信息
    public final static String NEWPOTENTVISITCONTEXT = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/newPotentVisitContext";
    // 添加状态
    public final static String CHANGEQKSTATE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/changeQkState";
    // 搜索
    public final static String SEARCHCLIENTBYKEY = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/searchClientByKey";
    // 排序
    public final static String SEARCHCLIENTBYORDER = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/searchClientByOrder";
    // 搜索
    public final static String GETPOTENTIALCLIENTBYNAME = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getPotentialClientByName";
    // 搜索
    public final static String GETPOTENTIALCLIENTBYMOBILE = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/PotentialClient/getPotentialClientByMobile";
    // 店长获取路救列表
    public final static String GETLUJIUORDERS = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/BossCenter/getLujiuOrders";
    // 店长获取路救状态和数量
    public final static String GETLUJIUTYPESTATE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/getLujiuTypeState";
    // 店长获取路救记录
    public final static String GETLUJIURECORD = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/getLujiuRecord";
    // 指派任务
    public final static String DISTRIBUTELUJIU = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/distributeLujiu";
    // 获取工作人员
    public final static String GETWORKERS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/getWorkers";
    // 店长直接处理路救
    public final static String BOSSDEALLUJIU = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/bossDealLujiu";
    // 获取路救订单员工
    public final static String GETLUJIUORDERSREPAIR = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Repair/getLujiuOrders";
    // 获取路救统计信息
    public final static String REQLUJIUSTATISTICS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqLujiuStatistics";
    // 接受路救
    public final static String ACCEPTLUJIUJOB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Repair/acceptLujiuJob";
    // 路救到达
    public final static String ARRIVELUJIUJOB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Repair/arriveLujiuJob";
    // 完成路救
    public final static String OVERLUJIUJOB = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Repair/overLujiuJob";
    // 变更路救
    public final static String REQLUJIUCHANGE = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Repair/reqLujiuChange";
    // 员工获取路救历史
    public final static String GETLUJIUHISTORY = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Repair/getLujiuHistory";
    // 获取路救详情
    public final static String GETLUJIUDETAIL = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/getLujiuDetail";
    // 利润统计和人员统计（店长端）
    public final static String REQLJSTGROUPWORKERS = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqLjStGroupWorkers";
    // 工作人员端任务统计（工人端）
    public final static String REQLJSTGROUPFORSELF = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqLjStGroupForSelf";
    // 获取人员详情
    public final static String GETLUJIUBYWORKER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/getLujiuByWorker";
    // 获取路救人员和数据
    public final static String GETLUJIUWORKER = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/getLujiuWorker";


    public static String WEIXINappID = "wx0600b3f50bbba97a";// "wx7312102e788369e3";wxdc461b2151a0aedc
    public static String WEIXINappSecret = "a052564d900b0e29ccd86949a2cdc9df";// "d4624c36b6795d1d99dcf0547af5443d";bbb5e83c545e95f3441bdb6627061d43

    public static String QQID = "1105158207";// "100424468";1104778129
    public static String QQSecret = "UvwFTyytZYTbDr8x";// "c7394704798a158208a74ab60104f0ba";YoOcPbPxEFpNbnUg

    /**
     * 邮箱正则
     */
    public static final Pattern EMAIL_PATTERN = Pattern
            .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    /**
     * 密码正则
     */
    public static final Pattern PASSWORD_PATTERN = Pattern
            .compile("[a-zA-Z0-9]{6,20}");

    /**
     * 手机号正则
     */
    public static final Pattern TEL_PATTERN = Pattern.compile("1\\d{10}");

    /**
     * 车牌号正则
     */
    public static final Pattern CANNO = Pattern
            .compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

}
