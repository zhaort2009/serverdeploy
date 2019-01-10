package com.sdt.serverdeploy.Utility;

import java.io.File;

public class MsgHandler {

    private  String mMessage;

    public MsgHandler(String mMessage) {
        this.mMessage = mMessage;
    }
    public String dealMsg(){
        String dirStr = "/home/zhaort/MyService_jar/";
        File file = new File(dirStr);
        String cmdStr = "",result = "";
        if(mMessage.equals("check")){
            cmdStr = "sh checkZhaortService.sh";
            result = RunShellUtil.execCmd(cmdStr,file);
            if(result.equals(""))
                result = "no server running";
            System.out.println(result);
        }else if(mMessage.equals("start")){
            cmdStr = "sh startZhaortService.sh";
            result = RunShellUtil.execCmd(cmdStr,file);
            System.out.println(result);
            if(result.equals(""))
                result = "start succeed!";
        }else if(mMessage.equals("stop")){
            cmdStr = "sh stopZhaortService.sh";
            result = RunShellUtil.execCmd(cmdStr,file);
            System.out.println(result);
            if(result.equals(""))
                result = "stop succeed!";
        }
        return result;
    }

}
