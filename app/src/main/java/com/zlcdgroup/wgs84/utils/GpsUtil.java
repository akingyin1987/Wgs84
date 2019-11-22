package com.zlcdgroup.wgs84.utils;

import android.support.annotation.NonNull;
import com.zlcdgroup.wgs84.db.GpsVo;
import java.util.List;

/**
 * * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #
 *
 * @ Description:                                          #
 * @ Author king
 * @ Date 2016/11/7 17:22
 * @ Version V1.0
 */

public class GpsUtil {

  /**
   * 获取坪均数
   * @param gpsVos
   * @return
   */
  public synchronized   static GpsVo   findAverageGps(@NonNull List<GpsVo>  gpsVos){
        double   latcount = 0.0 ,lngcount=0.0;
        for(GpsVo  gpsVo : gpsVos){
          latcount=latcount+gpsVo.lat;
          lngcount=lngcount+gpsVo.lng;
        }
       return new GpsVo(latcount/gpsVos.size(),lngcount/gpsVos.size());
  }

  /**
   * 通过计算判断是否增加或移出
   * @param gpsVos
   * @param gpsVo
   */
  public synchronized   static    void    addCacheOrRemove(@NonNull List<GpsVo>  gpsVos,GpsVo   gpsVo){
    if(gpsVos.size() == 0){
      gpsVos.add(gpsVo);
      return;
    }
    if(gpsVos.size() == 50){
      return;
    }
    GpsVo   average  =  findAverageGps(gpsVos);

    if(null != average){
      if(average.eq(gpsVo)){
        double  difference = 0.0;
        int     postion=0;
        for(int i=0;i<gpsVos.size();i++){
         double dis = gpsVos.get(i).difference(gpsVo);
          if(dis>0.0005){
            if(dis>difference){
              difference = dis;
              postion = i;
            }
          }
        }
        if(difference>0){
          gpsVos.remove(postion);
        }
        gpsVos.add(gpsVo);

        return;
      }

    }

  }
}
