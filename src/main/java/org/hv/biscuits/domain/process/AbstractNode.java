package org.hv.biscuits.domain.process;

import java.sql.SQLException;

/**
 * @author wujianchuan
 */
public abstract class AbstractNode implements Node {
    private Node preNode;
    private Node nextNode;
    private boolean isTop;
    private boolean isTail;

    /**
     * 流程通过时执行的操作
     *
     * @param dataUuid 业务数据的数据标识
     * @return 是否执行成功
     */
    public abstract boolean doAccept(String dataUuid);

    /**
     * 流程驳回时执行的操作
     *
     * @param dataUuid 业务数据的数据标识
     * @return 是否执行成功
     */
    public abstract boolean doRejection(String dataUuid);

    @Override
    public void setPreNode(Node preNode) {
        this.preNode = preNode;
    }

    @Override
    public Node getPreNode() {
        return this.preNode;
    }

    @Override
    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public Node getNextNode() {
        return this.nextNode;
    }

    @Override
    public void beTop() {
        this.isTop = true;
    }

    @Override
    public boolean isTop() {
        return this.isTop;
    }

    @Override
    public void beTail() {
        this.isTail = true;
    }

    @Override
    public boolean isTail() {
        return this.isTail;
    }

    @Override
    public boolean accept(Context context) throws SQLException {
        boolean success = this.doAccept(context.getDataUuid());
        if (success) {
            context.setCurrentNode(nextNode);
        }
        return success;
    }

    @Override
    public boolean rejection(Context context) throws SQLException {
        boolean success = this.doRejection(context.getDataUuid());
        if (success) {
            context.setCurrentNode(preNode);
        }
        return success;
    }
}
