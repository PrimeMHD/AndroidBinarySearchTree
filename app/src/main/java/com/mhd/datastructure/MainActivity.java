package com.mhd.datastructure;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.icu.lang.UCharacterEnums;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportHelper;

import com.mhd.datastructure.View.CanvasView;
import com.mhd.datastructure.View.TreeView;

import org.w3c.dom.Text;

public class MainActivity extends SupportActivity {

    private static final String TAG = "MainActivity";
    private CanvasView canvas;
    private Paint mPaint;
    private DisplayMetrics dm;
    private TextView textView_OperationWindow;
    private TreeView treeView;
    private EditText editText_DataToInsert;
    private EditText editText_DataToDelete;
    private EditText editText_DataToSelect;
    private Button button_confirm_delete;
    private Button button_confirm_insert;
    private Button button_confirm_select;
    private BinaryTree binaryTree;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_main);
        initView();


    }



    private void initView() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        treeView = (TreeView) findViewById(R.id.tv1);
        binaryTree = new BinaryTree();
        binaryTree.setTreeView(treeView);
        treeView.setBinaryTree(binaryTree);
        //这里产生了很强的耦合
        treeView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                treeView.post(new Runnable() {
                    @Override
                    public void run() {
                        treeView.setRootXY();
                    }
                });
            }
        });


        binaryTree.getTreeView().setScreenHeight(dm.heightPixels);
        binaryTree.getTreeView().setScreenWidth(dm.widthPixels);
        //binaryTree.insertNode(50);
        //binaryTree.insertNode(20);
        button_confirm_insert = (Button) findViewById(R.id.button_confirm_insert);
        editText_DataToInsert = (EditText) findViewById(R.id.et_DataToInsert);
        textView_OperationWindow = (TextView) findViewById(R.id.textView_OperationWindow);
        editText_DataToDelete = (EditText) findViewById(R.id.et_DataToDelete);
        button_confirm_delete = (Button) findViewById(R.id.button_confirm_delete);
        button_confirm_select = (Button) findViewById(R.id.button_confirm_select);
        editText_DataToSelect = (EditText) findViewById(R.id.et_DataToSelect);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView_OperationWindow.setMovementMethod(ScrollingMovementMethod.getInstance());

        button_confirm_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insertDataString = editText_DataToInsert.getText().toString();
                if (!insertDataString.equals("")) {
                    int insertData = Integer.parseInt(insertDataString);
                    binaryTree.insertNode(insertData);
                }
            }
        });

        button_confirm_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deleteDataString = editText_DataToDelete.getText().toString();
                if (!deleteDataString.equals("")) {
                    int deleteData = Integer.parseInt(deleteDataString);
                    if (!binaryTree.deleteNode(deleteData)) {
                        Toast.makeText(MainActivity.this, "没有找到要删的节点", Toast.LENGTH_LONG).show();
                    }


                }

            }
        });
        button_confirm_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDataString = editText_DataToSelect.getText().toString();
                if (!selectDataString.equals("")) {
                    int selectData = Integer.parseInt(selectDataString);
                    if (!treeView.selectNode(selectData)) {
                        Toast.makeText(MainActivity.this, "没有查找到该节点", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


        binaryTree.setBTOperationListener(new BinaryTree.operationListener() {
            @Override
            public void onNodeInsert(BTNode btNode) {
                Log.d(TAG,"PRINTinsert");
                printInsert(btNode);
            }

            @Override
            public void onNodeDelete(BTNode btNode, boolean isRoot) {
                printDelete(btNode, isRoot);
            }

            @Override
            public void onNodeSelect(int dataToSelect, BTNode btNode) {
                printSelect(dataToSelect, btNode);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,Activity_AboutApp.class));
            return true;
        }
        else if(id==R.id.action_learnBST){
            startActivity(new Intent(this,Activity_LearnBST.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "land do nothing is ok");
            // land do nothing is ok
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "port do nothing is ok");
            // port do nothing is ok
        }
    }


    private void printInsert(int data) {


        textView_OperationWindow.append("插入了一个值为" + data + "的节点\n");
    }

    private void printInsert(BTNode btNode) {
        textView_OperationWindow.append("插入了一个值为" + btNode.getData() + "的节点\n");
        autoScroll();

    }

    private void printDelete(int data, boolean isRoot) {
        if (isRoot) {
            textView_OperationWindow.append("删除了值为" + data + "根节点\n");

        } else {
            textView_OperationWindow.append("删除了一个值为" + data + "的节点\n");

        }
    }

    private void printDelete(BTNode btNode, boolean isRoot) {
        if (isRoot) {
            textView_OperationWindow.append("删除了值为" + btNode.getData() + "根节点\n");

        } else {
            textView_OperationWindow.append("删除了一个值为" + btNode.getData() + "的节点\n");

        }
        autoScroll();

    }

    private void printSelect(int dataToSelect, BTNode btNode) {
        textView_OperationWindow.append("查找值为" + dataToSelect + "的节点,");
        if (btNode == null)
            textView_OperationWindow.append("没有找到\n");
        else
            textView_OperationWindow.append("查找成功\n");
        autoScroll();
    }
    private void autoScroll(){
        int offset=textView_OperationWindow.getLineCount()*textView_OperationWindow.getLineHeight();
        if(offset>(textView_OperationWindow.getHeight()-textView_OperationWindow.getLineHeight()-20)){
            textView_OperationWindow.scrollTo(0,offset-textView_OperationWindow.getHeight()+textView_OperationWindow.getLineHeight()+5);
        }
    }


}
