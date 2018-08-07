package com.example.currentplacedetailsonmap.tool;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class WriteDataQueue {
    @SuppressLint({"HandlerLeak"})
    Handler han = new C05831();
    private int han_delay = 100;
    private final int han_what = 100;
    private LinkedList list = new LinkedList();
    private QueueListener mListener;
    private byte[] send_data;
//    private final int send_what = TinkerReport.KEY_APPLIED_SUCC_COST_5S_LESS;
    private Timer timer;
    private int timer_Num = 0;
    private int timer_delay = 1000;
    private final int timer_maxNum = 3;

    class C05831 extends Handler {
        C05831() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (WriteDataQueue.this.timer == null && !WriteDataQueue.this.QueueEmpty()) {
                        WriteDataQueue.this.startTime(WriteDataQueue.this.deQueue());
                    }
                    WriteDataQueue.this.start();
                    return;
//                case TinkerReport.KEY_APPLIED_SUCC_COST_5S_LESS /*200*/:
//                    WriteDataQueue.this.mListener.deQueue((byte[]) msg.obj);
//                    return;
                default:
                    return;
            }
        }
    }

    public interface QueueListener {
        void deQueue(byte[] bArr);
    }

    public WriteDataQueue(QueueListener mListener) {
        this.mListener = mListener;
    }

    public void start() {
        if (this.han.hasMessages(100)) {
            this.han.removeMessages(100);
        }
        this.han.sendEmptyMessageDelayed(100, (long) this.han_delay);
    }

    public void close() {
        if (this.han.hasMessages(100)) {
            this.han.removeMessages(100);
        }
    }

    private void startTime(final byte[] data) {
        if (data != null && data.length > 4) {
            if (data[3] == (byte) -2 || data[3] == (byte) 1) {
                this.mListener.deQueue(data);
                return;
            }
            this.send_data = data;
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                public void run() {
                    Message msg = new Message();
//                    msg.what = TinkerReport.KEY_APPLIED_SUCC_COST_5S_LESS;
                    msg.obj = data;
                    WriteDataQueue.this.han.sendMessage(msg);
                    WriteDataQueue.this.timer_Num = WriteDataQueue.this.timer_Num + 1;
                    if (WriteDataQueue.this.timer_Num >= 3) {
                        WriteDataQueue.this.closeTime();
                    }
                }
            }, 0, (long) this.timer_delay);
        }
    }

    public void hasData(byte[] data) {
        if (this.send_data == null || this.send_data.length < 4) {
            closeTime();
        } else if (data.length > 4 && data[3] == this.send_data[3]) {
            closeTime();
        }
    }

    private void closeTime() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
            this.send_data = null;
            this.timer_Num = 0;
        }
    }

    public void clear() {
        this.list.clear();
    }

    public boolean QueueEmpty() {
        return this.list.isEmpty();
    }

    public void enQueue(byte[] o) {
        this.list.addLast(o);
    }

    public byte[] deQueue() {
        if (this.list.isEmpty()) {
            return null;
        }
        return (byte[]) this.list.removeFirst();
    }

    public int QueueLength() {
        return this.list.size();
    }

    public Object QueuePeek() {
        return this.list.getFirst();
    }
}
