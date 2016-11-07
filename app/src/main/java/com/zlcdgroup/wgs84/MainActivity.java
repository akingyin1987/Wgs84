package com.zlcdgroup.wgs84;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zlcdgroup.wgs84.db.GpsVo;
import com.zlcdgroup.wgs84.utils.GpsUtil;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseNfcActivity implements BDLocationListener {

  @BindView(R.id.tv_gpsinfo) TextView tvGpsinfo;
  @BindView(R.id.tv_gpscache) TextView tvGpscache;
  @BindView(R.id.tv_count) TextView tvCount;
  @BindView(R.id.btn_startloc) Button btnStartloc;
  private LocationClient mLocationClient = null;
  PowerManager pm = null;
  PowerManager.WakeLock mWakeLock = null;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "wgs84");
    mLocationClient = new LocationClient(this);     //声明LocationClient类
    mLocationClient.registerLocationListener(this);
    initLocation();
  }

  private void initLocation() {
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(
        LocationClientOption.LocationMode.Device_Sensors);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
    option.setCoorType(BDLocation.BDLOCATION_WGS84_TO_GCJ02);//可选，默认gcj02，设置返回的定位结果坐标系
    int span = 2000;
    option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
    option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
    option.setOpenGps(true);//可选，默认false,设置是否使用gps
    //option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
    // option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
    // option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
    // option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
    option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
    option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
    mLocationClient.setLocOption(option);
    mLocationClient.start();
  }


  private List<GpsVo>  gpsVos  = new LinkedList<>();

  @Override public void onReceiveLocation(BDLocation bdLocation) {
    if (null != bdLocation) {
      if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
        tvGpsinfo.setText("精度："
            + bdLocation.getRadius()
            + "米  <br> 经度："
            + bdLocation.getLatitude()
            + " <br>纬度："
            + bdLocation.getLongitude());
          if(locstatus){
            GpsUtil.addCacheOrRemove(gpsVos,new GpsVo(bdLocation.getLatitude(),bdLocation.getLongitude()));
            if(gpsVos.size() == 50){
              locstatus = false;
            }
            endLoc();
          }

      } else {
        tvGpsinfo.setText("gps定位失败");
      }
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if (null != mWakeLock) {
      mWakeLock.acquire();
    }
  }

  @Override public void handleTag(String rfid, String block0) {

  }

  @Override protected void onPause() {
    super.onPause();
    if (null != mWakeLock) {
      mWakeLock.release();
    }
  }

  public    boolean   locstatus=false;//定位状态
  @OnClick(R.id.btn_startloc)
  public   void  btn_startloc(){
    if(locstatus){
      startLoc();
      btnStartloc.setText("停止定位");
    }else{
      btnStartloc.setText("开始定位");
      endLoc();
    }
    locstatus = !locstatus;
  }

  public   static   String   startBlueHtml="<font color = 'blue'>";
  public   static   String   startRedHtml="<font color = 'red'>";
  public   static   String   endHtml="</font><br/>";
  public   static   String   finalHtml="</font>";




  public synchronized void   startLoc(){
    gpsVos.clear();
    tvGpscache.setText(Html.fromHtml("gps缓存数： <br> 当前经度：  <br>当前纬度："));
  }

  public   void    endLoc(){
   StringBuffer   stringBuffer = new StringBuffer();
    GpsVo  gpsVo  = GpsUtil.findAverageGps(gpsVos);
   stringBuffer.append(gpsVos.size()<50?startRedHtml:startBlueHtml).append("gps缓存数：")
       .append(gpsVos.size()).append(endHtml)
       .append(startBlueHtml).append(" 当前经度：").append(gpsVo.lat)
       .append(endHtml).append(startBlueHtml).append("当前纬度：").append(gpsVo.lng).append(finalHtml);
    tvGpscache.setText(Html.fromHtml(stringBuffer.toString()));
  }
}
