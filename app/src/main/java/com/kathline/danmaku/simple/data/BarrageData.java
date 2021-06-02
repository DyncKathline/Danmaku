package com.kathline.danmaku.simple.data;

import com.kathline.danmaku.model.DataSource;

public class BarrageData implements DataSource {

    private String content;
    private int type;
    private int pos;

    public BarrageData(String content, int type, int pos) {
        this.content = content;
        this.type = type;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }
}
