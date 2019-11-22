package com.zlcdgroup.wgs84.db;

import java.io.Serializable;

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

public class GpsVo implements Serializable{

    public    double   lat;

    public    double   lng;

  public GpsVo() {
  }

  public GpsVo(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  /**
   * 比较
   * @param gpsVo
   * @return
   */
  public   boolean  eq(GpsVo  gpsVo){
    if(Math.abs(lat-gpsVo.lat) < 0.00005 && Math.abs(lng-gpsVo.lng)<0.00005){
      return  true;
    }
    return  false;
  }

  //差值
  public   double  difference(GpsVo  gpsVo){
    return  Math.abs(lat-gpsVo.lat)+Math.abs(lng-gpsVo.lng);
  }
}
