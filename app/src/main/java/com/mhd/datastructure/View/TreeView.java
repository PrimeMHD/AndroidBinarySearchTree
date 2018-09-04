package com.mhd.datastructure.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.mhd.datastructure.BTNode;
import com.mhd.datastructure.BinaryTree;
import com.mhd.datastructure.MainActivity;
import com.mhd.datastructure.R;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class TreeView extends View {
    private boolean RootXYsetted = false;
    private int TreeMaxWidth;
    private int TreeMaxHeight;
    private int LayerMargin;
    private int NodeRadius;
    private int NodeCenterColor;
    private int NodeRingWidth;
    private int NodeRingColor;
    private String TextContent;
    private int TextSize;
    private int TextColor;
    private boolean isTextDisplay;
    private int LineColor;
    private int LineWidth;

    private static final int DEFAULT_TREE_MAXWIDTH = 100;
    private static final int DEFAULT_TREE_MAXHEIGHT = 200;
    private static final int DEFAULT_LAYER_MARGIN = 20;
    private static final int DEFAULT_NODE_RADIUS = 10;
    private static final String DEFAULT_NODE_CENTERCOLOR = "#eeff06";
    private static final int DEFAULT_NODE_RINGWIDTH = 3;
    private static final String DEFAULT_NODE_RINGCOLOR = "#FF7281E1";
    private static final String DEFAULT_TEXTCONTENT = " ";
    private static final int DEFAULT_TEXTSIZE = 30;
    private static final String DEFAULT_TEXTCOLOR = "#FF000000";
    private static final boolean DEFAULT_ISTEXTDISPLAY = true;
    private static final String DEFAULT_LINE_COLOR = "#FF000000";
    private static final int DEFAULT_LINE_WIDTH = 2;
    private static final String DEFAULT_NODE_EMPHASIS_COLOR = "#FFED252C";
    private static final String DEFAULT_RING_EMPHASIS_COLOR = "#FFFFC552";
    private static final String DEFAULT_TEXT_EMPHASIS_COLOR = "#FFFFFF";
    private static final String DEFAULT_LINE_TORIGHT_COLOR="#00E300";
    private static final String DEFAULT_LINE_TOLEFT_COLOR="#E8AE0C";
    private static final String TAG = "TreeView";
    private static final int CHG_ML_WHEN_INSERT=0x1;
    private static final int CHG_ML_WHEN_DELETE=0x2;
    private static final int DIRECTION_RIGHT=0x1;
    private static final int DIRECTION_LEFT=0x2;
    private long mStartTime = -1;
    private long mStartOffset = 1000;
    private long mDuration = 2000;



    private boolean doEmphasis = false;
    private NodeOnTreeView nodeToEmphasize = null;
    private BinaryTree binaryTree;
    private Paint mPaint;
    private Canvas mCanvas;
    private int ScreenHeight;
    private int ScreenWidth;
    private int root_x;
    private int root_y;
    private int maxLayer = 0;
    private int verticalMarginDelta;
    private int minVerticalMargin;
    private int layerMargin;
    private List<Integer> MarginList = new ArrayList<Integer>();

    private Map<BTNode, NodeOnTreeView> NodeOnTreeList = new HashMap<>();

    public TreeView(Context context) {
        this(context, null);
    }


    public TreeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TreeView);
        for (int i = 0; i < a.length(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TreeView_TreeMaxWidth:
                    TreeMaxWidth = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TREE_MAXWIDTH, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.TreeView_TreeMaxHeight:
                    TreeMaxHeight = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TREE_MAXHEIGHT, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.TreeView_LayerMargin:
                    LayerMargin = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LAYER_MARGIN, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.TreeView_NodeRadius:
                    NodeRadius = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_NODE_RADIUS, getResources().getDisplayMetrics()
                    ));
                    verticalMarginDelta = (int) (NodeRadius * 1.5);
                    minVerticalMargin = 2 * NodeRadius;
                    layerMargin = 5 * NodeRadius;
                    break;
                case R.styleable.TreeView_NodeCenterColor:
                    NodeCenterColor = a.getColor(attr, Color.parseColor(DEFAULT_NODE_CENTERCOLOR));
                    break;
                case R.styleable.TreeView_NodeRingColor:
                    NodeRingColor = a.getColor(attr, Color.parseColor(DEFAULT_NODE_RINGCOLOR));
                    break;
                case R.styleable.TreeView_NodeRingWidth:
                    NodeRingWidth = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_NODE_RINGWIDTH, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.TreeView_TextContent:
                    TextContent = a.getString(attr);
                    break;
                case R.styleable.TreeView_TextColor:
                    TextColor = a.getColor(attr, Color.parseColor(DEFAULT_TEXTCOLOR));
                    break;
                case R.styleable.TreeView_TextSize:
                    TextSize = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXTSIZE, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.TreeView_isTextDisplay:
                    isTextDisplay = a.getBoolean(attr, DEFAULT_ISTEXTDISPLAY);
                    break;
                case R.styleable.TreeView_LineColor:
                    LineColor = a.getColor(attr, Color.parseColor(DEFAULT_LINE_COLOR));
                    break;
                case R.styleable.TreeView_LineWidth:
                    LineWidth = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LINE_WIDTH, getResources().getDisplayMetrics()
                    ));
                    break;
                default:
                    break;

            }
        }
        a.recycle();
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


    }

    public void insertNode(NodeOnTreeView nodeOnTreeView) {
        Log.d(TAG, "执行了insert方法");
        Log.d(TAG, "ScreenWidth=" + ScreenWidth);


        NodeOnTreeList.put(nodeOnTreeView.getBtNode(), nodeOnTreeView);


        if (nodeOnTreeView.getLayer() > maxLayer || nodeOnTreeView.getLayer() == 0) {
            changeMarginList(CHG_ML_WHEN_INSERT);
            maxLayer = nodeOnTreeView.getLayer();
        }

        reshapeTree();
        //redrawTree();
        invalidate();

    }


    private void changeMarginList(int Situation) {

        Log.d(TAG,"maxLayer="+maxLayer);
        switch (Situation){
            case CHG_ML_WHEN_INSERT:
                for (int i = 0; i < MarginList.size(); i++) {
                    MarginList.set(i, MarginList.get(i) + (maxLayer - i + 1) * verticalMarginDelta);
                }
                MarginList.add(minVerticalMargin);
                break;
            case CHG_ML_WHEN_DELETE:
                for (int i = 0; i < MarginList.size(); i++) {
                    MarginList.set(i, MarginList.get(i) - (maxLayer - i + 1) * verticalMarginDelta);
                }
                MarginList.remove(MarginList.size()-1);
        }

    }


    public void deleteNode(BTNode btNode) {
        //NodeOnTreeList.remove(btNode);
        refreshNodeOnTreeList();
        reshapeTree();
        //redrawTree();
        invalidate();


    }

    private void redrawTree(Canvas canvas) {
        //TODO
        //ADD ANIMATION EFFECT
        drawTree(canvas);

    }

    private void drawTree(Canvas canvas) {
        Log.d(TAG, "执行了drawTree方法");

        for (Map.Entry<BTNode, NodeOnTreeView> entry : NodeOnTreeList.entrySet()) {
            drawNode(canvas, entry.getValue());
        }


        linkAllNodes(canvas);
    }

    private void drawNode(Canvas canvas, NodeOnTreeView nodeOnTreeView) {
        Log.d(TAG, "调用了drawNode方法");
        Log.d(TAG, nodeOnTreeView.getBtNode().getData() + "");
        Log.d(TAG, "posx=" + nodeOnTreeView.getPosx());
        Log.d(TAG, "posy=" + nodeOnTreeView.getPosy());
        Log.d(TAG, "layer" + nodeOnTreeView.getLayer());
        Log.d(TAG, "rank=" + nodeOnTreeView.getRank());
        drawCenterCircle(canvas, nodeOnTreeView);
        drawRing(canvas, nodeOnTreeView);
        drawCenterText(canvas, nodeOnTreeView);

    }

    private void drawCenterCircle(Canvas canvas, NodeOnTreeView nodeOnTreeView) {
        Log.d(TAG, "调用了drawCenterCircle方法");

        mPaint.setColor(NodeCenterColor);
        mPaint.setStyle(Paint.Style.FILL);


        if (canvas == null) {
            Log.d(TAG, "canvas is null");
        } else if (mPaint == null) {
            Log.d(TAG, "mPaint is null");
        }

        //canvas.drawCircle(100,100,80,mPaint);

        canvas.drawCircle(nodeOnTreeView.getPosx(), nodeOnTreeView.getPosy(), NodeRadius, mPaint);
    }

    private void drawRing(Canvas canvas, NodeOnTreeView nodeOnTreeView) {
        Log.d(TAG, "调用了drawRing方法");

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(NodeRingColor);
        mPaint.setStrokeWidth(NodeRingWidth);
        canvas.drawCircle(nodeOnTreeView.getPosx(), nodeOnTreeView.getPosy(), NodeRadius, mPaint);

    }

    private void emphasizeNode(final Canvas canvas, final NodeOnTreeView nodeOnTreeView) {

        Log.d(TAG, "doEmphasize");
        //canvas.save();
        drawTree(canvas);
        //canvas.saveLayer(0, 0, 1000, 1000, mPaint, Canvas.ALL_SAVE_FLAG);
        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        mPaint.setColor(Color.parseColor(DEFAULT_NODE_EMPHASIS_COLOR));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(nodeOnTreeView.getPosx(), nodeOnTreeView.getPosy(), NodeRadius + 1, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(DEFAULT_RING_EMPHASIS_COLOR));
        mPaint.setStrokeWidth(NodeRingWidth);
        canvas.drawCircle(nodeOnTreeView.getPosx(), nodeOnTreeView.getPosy(), NodeRadius + 1, mPaint);
        mPaint.setColor(Color.parseColor(DEFAULT_TEXT_EMPHASIS_COLOR));
        mPaint.setTextSize(TextSize);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setStrokeWidth(0);
        TextContent = nodeOnTreeView.getBtNode().getData() + "";
        float textWidth = mPaint.measureText(TextContent);
        canvas.drawText(TextContent, nodeOnTreeView.getPosx() - textWidth / 2, nodeOnTreeView.getPosy() + TextSize / 2, mPaint);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "睡醒了");
                invalidate();
            }
        }, 1000); // 延时1秒

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.d(TAG,"readyToSleep");
//                    Thread.sleep(mDuration);
//                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
//                    drawTree(canvas);
//                    //drawNode(canvas,nodeOnTreeView);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        drawTree(canvas);

    }

    private void drawCenterText(Canvas canvas, NodeOnTreeView nodeOnTreeView) {

        if (!isTextDisplay) {
            return;
        }
        mPaint.setColor(TextColor);
        mPaint.setTextSize(TextSize);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setStrokeWidth(0);
        TextContent = nodeOnTreeView.getBtNode().getData() + "";
        float textWidth = mPaint.measureText(TextContent);
        canvas.drawText(TextContent, nodeOnTreeView.getPosx() - textWidth / 2, nodeOnTreeView.getPosy() + TextSize / 2, mPaint);


    }
    private void linkAllNodes(Canvas canvas) {
        BTNode cur;
        cur = binaryTree.getRoot();
        if (cur == null)
            return;
        Stack<BTNode> stack = new Stack<BTNode>();
        stack.push(cur);
        while (!stack.empty()) {
            cur = stack.pop();
            if (cur.getRightChild() != null) {
                stack.push(cur.getRightChild());
                drawLine(canvas, NodeOnTreeList.get(cur), NodeOnTreeList.get(NodeOnTreeList.get(cur).getBtNode().getRightChild()),DIRECTION_RIGHT);
            }
            if (cur.getLeftChild() != null) {
                stack.push(cur.getLeftChild());
                drawLine(canvas, NodeOnTreeList.get(cur), NodeOnTreeList.get(NodeOnTreeList.get(cur).getBtNode().getLeftChild()),DIRECTION_LEFT);

            }
        }
    }
    private void drawLine(Canvas canvas, NodeOnTreeView fromNode, NodeOnTreeView toNode,int direction) {
        if(direction==DIRECTION_LEFT)
            mPaint.setColor(Color.parseColor(DEFAULT_LINE_TOLEFT_COLOR));
        else if(direction==DIRECTION_RIGHT)
            mPaint.setColor(Color.parseColor(DEFAULT_LINE_TORIGHT_COLOR));

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(LineWidth);
        canvas.drawLine(fromNode.getPosx(), fromNode.getPosy() + NodeRadius, toNode.getPosx(), toNode.getPosy() - NodeRadius, mPaint);
    }

    public void reshapeTree() {
        NodeOnTreeView nodeOnTreeView;
        if (maxLayer == 0) {
            for (Map.Entry<BTNode, NodeOnTreeView> entry : NodeOnTreeList.entrySet()) {
                nodeOnTreeView = entry.getValue();
                nodeOnTreeView.setPosx(root_x);
                nodeOnTreeView.setPosy(root_y);
                NodeOnTreeList.put(nodeOnTreeView.getBtNode(), nodeOnTreeView);

            }
            return;
        }
        for (Map.Entry<BTNode, NodeOnTreeView> entry : NodeOnTreeList.entrySet()) {
            //for(int index=0;index<NodeOnTreeList.size();index++){
            nodeOnTreeView = entry.getValue();
            //NodeOnTreeList.get(index);
            int nodeOnTreeViewX = root_x;
            for (int i = 1; i <= nodeOnTreeView.getLayer(); i++) {
                //Log.d(TAG,""+i);

                nodeOnTreeViewX -= MarginList.get(i);
            }

            nodeOnTreeViewX += nodeOnTreeView.getRank() * 2 * MarginList.get(nodeOnTreeView.getLayer());

            nodeOnTreeView.setPosx(nodeOnTreeViewX);
            nodeOnTreeView.setPosy(root_y + nodeOnTreeView.getLayer() * layerMargin);
            NodeOnTreeList.put(nodeOnTreeView.getBtNode(), nodeOnTreeView);
            //NodeOnTreeList.set(index,nodeOnTreeView);
        }
    }

    public void refreshNodeOnTreeList() {
        NodeOnTreeList.clear();
        BTNode cur = binaryTree.getRoot();
        Stack<BTNode> BtnodeStack = new Stack<BTNode>();
        Stack<Integer> RankStack = new Stack<Integer>();
        Stack<Integer> LayerStack = new Stack<Integer>();
        int rank = 0;
        int layer = 0;
        int localMaxLayer = 0;
        while (cur != null) {
            if (layer > localMaxLayer)
                localMaxLayer = layer;
            NodeOnTreeList.put(cur, new NodeOnTreeView(cur, layer, rank));
            if (cur.getRightChild() != null) {
                BtnodeStack.push(cur.getRightChild());
                RankStack.push(2 * rank + 1);
                LayerStack.push(layer + 1);
            }

            if (cur.getLeftChild() != null) {
                rank = 2 * rank;
                layer++;
                cur = cur.getLeftChild();
            } else if (BtnodeStack.empty()) {
                Log.d(TAG,"localMaxLayer="+localMaxLayer);
                maxLayer = localMaxLayer;
                changeMarginList(CHG_ML_WHEN_DELETE);
                return;
            } else {
                rank = RankStack.pop();
                layer = LayerStack.pop();
                cur = BtnodeStack.pop();
            }

        }

    }


//    public void refreshNodeOnTreeView(NodeOnTreeView nodeOnTreeView){
//        int newLayer=0;
//        int newRank=0;
//        final BTNode targetNode=nodeOnTreeView.getBtNode();
//        BTNode Nodeptr=binaryTree.getRoot();
//        while(Nodeptr!=null) {
//            if(targetNode==Nodeptr){
//                nodeOnTreeView.setLayer(newLayer);
//                nodeOnTreeView.setRank(newRank);
//                return;
//            }
//            else if(targetNode.getData()>=Nodeptr.getData()){
//                newRank=2*newRank+1;
//                newLayer++;
//                Nodeptr=Nodeptr.getRightChild();
//            }else{
//                newRank*=2;
//                newLayer++;
//                Nodeptr=Nodeptr.getLeftChild();
//            }
//
//        }
//
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "调用了onDraw方法");
        //root_x=getWidth()/2;
        //Log.d(TAG,"rootx="+root_x+" rooty="+root_y);
        mCanvas = canvas;
        if (doEmphasis) {

            emphasizeNode(canvas, nodeToEmphasize);
            doEmphasis = false;
            nodeToEmphasize = null;
        } else {
            drawTree(canvas);

        }

    }

    public boolean selectNode(int data) {
        BTNode btNode = binaryTree.getBTNode(data);
        if (btNode == null) {
            return false;
        } else {

            nodeToEmphasize = NodeOnTreeList.get(btNode);
            doEmphasis = true;
            invalidate();

            return true;
        }


    }


    public int getScreenHeight() {
        return ScreenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        ScreenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return ScreenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        ScreenWidth = screenWidth;
//        root_x = ScreenWidth * 9 / 16;
//        int viewScreenX;
//        int viewScreenY;
//        root_x=getWidth()/2;
//        Log.d(TAG,"getLeft="+getLeft()+"getWidth="+getWidth());
//        root_y = 2 * NodeRadius;
    }

    public void setRootXY() {
        if (!RootXYsetted) {
            root_x = getWidth() / 2;
            root_y = 4 * NodeRadius;
        }
    }

    public BinaryTree getBinaryTree() {
        return binaryTree;
    }

    public void setBinaryTree(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }
}
