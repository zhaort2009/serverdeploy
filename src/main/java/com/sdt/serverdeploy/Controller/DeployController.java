package com.sdt.serverdeploy.Controller;
import com.sdt.serverdeploy.Service.MsgQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeployController {

    private MsgQueueService msgQueueService;

    @Autowired
    public void setMsgQueueService(MsgQueueService msgQueueService){
        System.out.println("Injected!");
        this.msgQueueService = msgQueueService;
    }

    @ResponseBody
    @RequestMapping(value="/test1",method = RequestMethod.GET)
    public String dealRequest(String msg){
        //调用MyServeice对象方法将msg入队
        msgQueueService.pushQueue(msg);
        return msgQueueService.doOneCmd();
    }

    @ResponseBody
    @RequestMapping(value="/test2",method = RequestMethod.GET)
    public String dealRequest2(){
        //调用MyServeice对象方法将msg入队
        int size = msgQueueService.getSize();
        String string = "";
        if(size!=0)
            string = msgQueueService.getAllMsg();
        else
            string = "blank in queue";
        return ""+size+" msg in Queue,Msg="+string;
    }
}
