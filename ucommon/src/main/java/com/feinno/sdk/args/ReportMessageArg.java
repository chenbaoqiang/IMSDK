package com.feinno.sdk.args;

import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.enums.ReportType;

import java.util.UUID;

/**
 * 发送消息回执时的消息参数
 */
public class ReportMessageArg {
    protected String messageID;
    protected String messageTo;
    private String imdnID;
    private ReportType reportType;
    private DirectedType directedType = DirectedType.NONE;
    private String target;

    /**
     * 构造一个消息参数
     * @param to 消息接收者
     * @param imdnId 消息的IMDN ID，可以通过{@link com.feinno.sdk.session.MessageSession}获得
     * @param reportType 报告类型，参见{@link ReportType}
     */
    public ReportMessageArg(String to, String imdnId, ReportType reportType) {
        this(to, imdnId, reportType, DirectedType.NONE, null);
    }

    /**
     * 构造一个消息参数
     * @param to 消息接收者
     * @param imdnId 消息的IMDN ID，可以通过{@link com.feinno.sdk.session.MessageSession}获得
     * @param reportType 报告类型，参见{@link ReportType}
     * @param directedType 发给给自己其他终端的类型
     */
    public ReportMessageArg(String to, String imdnId, ReportType reportType, DirectedType directedType) {
        this(to, imdnId, reportType, directedType, null);
    }

    /**
     * 构造一个消息参数
     * @param to 消息接收者
     * @param imdnId 消息的IMDN ID，可以通过{@link com.feinno.sdk.session.MessageSession}获得
     * @param reportType 报告类型，参见{@link ReportType}
     * @param directedType 发给给自己其他终端的类型
     * @param target 送达报告类型是群消息已送达、已读时,此字段需要填写群消息发送方的Uid,其它报告类型此字段填NULL
     */
    public ReportMessageArg(String to, String imdnId, ReportType reportType, DirectedType directedType, String target) {
        this.messageID = UUID.randomUUID().toString();
        this.messageTo = to;
        this.imdnID = imdnId;
        this.reportType = reportType;
        this.directedType = directedType;
        this.target = target;
    }

    public  String getImdnID() {
        return imdnID;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public DirectedType getDirectedType() { return directedType; }

    public String getTarget() {
        return target;
    }

}
