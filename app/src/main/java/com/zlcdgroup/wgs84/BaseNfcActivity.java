package com.zlcdgroup.wgs84;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.zlcdgroup.wgs84.utils.CHexConver;
import java.io.IOException;

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
 * @ Date 2016/11/7 15:21
 * @ Version V1.0
 */

public  abstract class BaseNfcActivity  extends AppCompatActivity {
  private NfcAdapter mAdapter;
  private PendingIntent mPendingIntent;
  private IntentFilter[] mFilters;
  private String[][] mTechLists;
  public MifareClassic mf = null;
  public Tag tagFromIntent = null;
  private int openNfc = 3;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = NfcAdapter.getDefaultAdapter(this);

    if (null == mAdapter) {
      showToast("当前终端不支持NFC");
    } else {

      mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(
          Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
      IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
      try {
        ndef.addDataType("*/*");
        mFilters = new IntentFilter[] { ndef, };
        mTechLists = new String[][] {
            new String[] { MifareClassic.class.getName() },
            new String[] { NfcA.class.getName() },
            new String[] { NfcB.class.getName() },
            new String[] { NfcF.class.getName() },
            new String[] { NfcV.class.getName() } };

      } catch (IntentFilter.MalformedMimeTypeException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }


  @Override
  protected void onStart() {

    super.onStart();
    if (null != mAdapter && !mAdapter.isEnabled()) {
      showToast("请打开NFC");
      if (openNfc >= 3) {
        openNfc = 3;
        return;
      }
      startActivity(new Intent("android.settings.NFC_SETTINGS"));
      openNfc++;
    }
  }



  @Override
  protected void onResume() {

    if(null != mAdapter){
      mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }


    super.onResume();
  }

  @Override
  protected void onPause() {
    if(null != mAdapter){
      mAdapter.disableForegroundDispatch(this);
    }

    super.onPause();
  }
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
      tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
      mf = MifareClassic.get(tagFromIntent);
    }
    String  block0="";
    try {
      String  rfid = CHexConver.getId(tagFromIntent.getId());
      if(null != mf){
        mf.connect();
        boolean  auth = mf.authenticateSectorWithKeyA(0,MifareClassic.KEY_DEFAULT);
        if(auth){
          block0 = CHexConver.bytesToHexStrings(mf.readBlock(0));
        }
      }
      handleTag(rfid,block0);
    }catch (Exception e){
      e.printStackTrace();
    }finally {
      if(null != mf){
        try {
          mf.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }


  public  abstract  void   handleTag(String  rfid,String  block0);


  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();

    openNfc = 0;
  }


  public   void  showToast(String msg){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
  }
}
