package com.example.saakshi.othell;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int boardsize = 8;
    public final static int north = 1;
    public final static int northeast = 2;
    public final static int northwest = 3;
    public final static int south = 4;
    public final static int southeast = 5;
    public final static int southwest = 6;
    public final static int east = 7;
    public final static int west = 8;
    boolean crossturn = true;
    MyButton[][] buttons;
    int black = 0;
    int white = 0;
    boolean flags = false;
    LinearLayout mainLayout;
    public static final int n = 8;
    LinearLayout rowLayouts[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        setup();
        //startgame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.newg) {
            setup();
        }
        return true;
    }


    public void setup() {
        crossturn = true;
super.setTitle("Black's turn");
        black = 0;
        white = 0;
        flags = false;
        buttons = new MyButton[n][n];
        rowLayouts = new LinearLayout[n];
        mainLayout.removeAllViews();
        for (int i = 0; i < n; i++) {
            rowLayouts[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
            params.setMargins(2, 2, 2, 2);
            //rowLayouts[i].setBackgroundColor(Color.GREEN);
            rowLayouts[i].setLayoutParams(params);
            rowLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayouts[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(2, 2, 2, 2);
                //buttons[i][j].setBackground(ContextCompat.getDrawable(this, R.drawable.circle));
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTextSize(50);


                rowLayouts[i].addView(buttons[i][j]);

            }


        }


        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                buttons[row][col].setBackgroundColor(Color.GREEN);
                buttons[row][col].a = 0;
                if (row == 3 && col == 3 || row == 4 && col == 4) {
                    buttons[row][col].setBackgroundColor(Color.WHITE);
                    buttons[row][col].a = 1;
                }
                if (row == 2 && col == 3 || row == 3 && col == 2 || row == 4 && col == 5 || row == 5 && col == 4) {
                    buttons[row][col].setBackgroundColor(Color.BLUE);
                    Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                    animation.setDuration(500);
                    buttons[row][col].startAnimation(animation);
                    animation.setRepeatCount(2);
                    buttons[row][col].a = 2;
                }
                if (row == 3 && col == 4 || row == 4 && col == 3) {
                    buttons[row][col].setBackgroundColor(Color.BLACK);
                    buttons[row][col].a = 3;
                }
            }
        }


    }


    // GridLayout layout = new GridLayout(boardsize, boardsize);


    public void startgame() {
        crossturn = true;

    }

    public void move1(MyButton button) {

        if (button.a == 2) {
            button.setBackgroundColor(Color.BLACK);
            button.a = 3;
        } else if (button.a == 4) {
            button.setBackgroundColor(Color.WHITE);
            button.a = 1;

        }

    }

    public void move2() {
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                if (buttons[i][j].a == 2) {
                    buttons[i][j].setBackgroundColor(Color.GREEN);
                    buttons[i][j].a = 0;

                } else if (buttons[i][j].a == 4) {
                    buttons[i][j].setBackgroundColor(Color.GREEN);
                    buttons[i][j].a = 0;
                }

            }

        }
    }

    public void markvalidcell(int i, int j, int flag) {

        checkvalidityindirection(south, i, j, flag);
        checkvalidityindirection(southeast, i, j, flag);
        checkvalidityindirection(southwest, i, j, flag);
        checkvalidityindirection(north, i, j, flag);
        checkvalidityindirection(northeast, i, j, flag);
        checkvalidityindirection(northwest, i, j, flag);
        checkvalidityindirection(east, i, j, flag);
        checkvalidityindirection(west, i, j, flag);
    }

    public void checkvalidityindirection(int direction, int i, int j, int flag) {
        int incri = 0;
        int incrj = 0;

        switch (direction) {
            case 1: {
                incri = -1;
                incrj = 0;
                break;
            }
            case 2: {
                incri = -1;
                incrj = 1;
                break;

            }
            case 3: {
                incri = -1;
                incrj = -1;
                break;
            }
            case 4: {
                incri = 1;
                incrj = 0;
                break;
            }
            case 5: {
                incri = 1;
                incrj = 1;
                break;
            }
            case 6: {
                incri = 1;
                incrj = -1;
                break;
            }
            case 7: {
                incri = 0;
                incrj = 1;
                break;
            }
            case 8: {
                incri = 0;
                incrj = -1;
                break;
            }

        }
        int k = i;
        int l = j;
        i = i + incri;
        j = j + incrj;
        if (flag == 1) {
            if (crossturn == true) {
                if (isValidPos(i, j) && buttons[i][j].a != 3
                        && buttons[i][j].a != 0) {
                    while (isValidPos(i, j) && buttons[i][j].a == 1) {
                        i = i + incri;
                        j = j + incrj;
                    }
                    if (isValidPos(i, j) && buttons[i][j].a == 3) {

                        i = i - incri;
                        j = j - incrj;
                        // dangerous
                        if (isValidPos(i, j)) {
                            buttons[i][j].setBackgroundColor(Color.BLACK);
                            buttons[i][j].a = 3;
                        }
                        i = i - incri;
                        j = j - incrj;
                        while (isValidPos(i, j) && buttons[i][j].a != 3
                                ) {
                            buttons[i][j].setBackgroundColor(Color.BLACK);
                            buttons[i][j].a = 3;
                            i = i - incri;
                            j = j - incrj;
                        }
                    }
                }
            } else {
                if (isValidPos(i, j) && buttons[i][j].a != 1
                        && buttons[i][j].a != 0) {
                    while (isValidPos(i, j) && buttons[i][j].a == 3) {
                        i = i + incri;
                        j = j + incrj;
                    }
                    if (isValidPos(i, j) && buttons[i][j].a == 1) {

                        i = i - incri;
                        j = j - incrj;
                        // dangerous
                        if (isValidPos(i, j)) {
                            buttons[i][j].setBackgroundColor(Color.WHITE);
                            buttons[i][j].a = 1;
                        }
                        i = i - incri;
                        j = j - incrj;
                        while (isValidPos(i, j) && buttons[i][j].a != 1) {
                            buttons[i][j].setBackgroundColor(Color.WHITE);
                            buttons[i][j].a = 1;
                            i = i - incri;
                            j = j - incrj;
                        }
                    }
                }

            }
        }
        if (flag == 2) {


            if (crossturn == false) {


                if (isValidPos(i, j) && buttons[i][j].a != 1
                        && buttons[i][j].a != 0) {
                    while (isValidPos(i, j) && buttons[i][j].a == 3) {
                        i = i + incri;
                        j = j + incrj;
                    }
                    if (isValidPos(i, j) && buttons[i][j].a == 0) {

                        buttons[i][j].setBackgroundColor(Color.RED);
                        Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                        animation.setDuration(500);
                        animation.setRepeatCount(1);
                        buttons[i][j].startAnimation(animation);

                        //buttons[i][j].setElevation(100f);
                        buttons[i][j].a = 4;
                    }
                }

            } else {


                if (isValidPos(i, j) && buttons[i][j].a != 3
                        && buttons[i][j].a != 0) {
                    while (isValidPos(i, j) && buttons[i][j].a == 1) {
                        i = i + incri;
                        j = j + incrj;
                    }
                    if (isValidPos(i, j) && buttons[i][j].a == 0) {

                        buttons[i][j].setBackgroundColor(Color.BLUE);
                        Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                        animation.setDuration(500);
                        buttons[i][j].startAnimation(animation);
                        animation.setRepeatCount(1);
                        //buttons[i][j].setElevation(100f);
                        buttons[i][j].a = 2;

                    }

                }
            }


        }


    }

    public boolean isValidPos(int i, int j) {
        return i >= 0 && i < boardsize && j >= 0 && j < boardsize;
    }

    @Override
    public void onClick(View v) {
        MyButton clickbutton = (MyButton) v;
        int i = 0, j = 0, l = 0, k = 0;
        for (i = 0; i < boardsize; i++) {
            for (j = 0; j < boardsize; j++) {
                if (buttons[i][j] == clickbutton) {
                    k = i;
                    l = j;
                    break;
                }

            }

        }
        if (clickbutton.a == 3 || clickbutton.a == 1
                || clickbutton.a == 0) {
            Toast.makeText(this, "invalidmove", Toast.LENGTH_SHORT).show();
        } else {
            if (clickbutton.a == 2 || clickbutton.a == 4) {
                move1(clickbutton);
                move2();
                markvalidcell(k, l, 1);
                crossturn = !crossturn;
                if (crossturn == false) {
                    for (i = 0; i < boardsize; i++) {
                        {
                            for (j = 0; j < boardsize; j++)
                                if (buttons[i][j].a == 1) {
                                    markvalidcell(i, j, 2);
                                }
                        }
                    }
                } else {
                    for (i = 0; i < boardsize; i++) {
                        {
                            for (j = 0; j < boardsize; j++)
                                if (buttons[i][j].a == 3) {
                                    markvalidcell(i, j, 2);
                                }
                        }
                    }

                }
                flags = true;

                for (i = 0; i < n; i++)
                    for (j = 0; j < n; j++) {
                        if (buttons[i][j].a == 4) {
                            flags = false;
                            break;
                        }
                        if (buttons[i][j].a == 2) {
                            flags = false;
                            break;
                        }

                    }

                if (flags == true) {
                    for (i = 0; i < n; i++)
                        for (j = 0; j < n; j++) {
                            if (buttons[i][j].a == 3)
                                black++;
                            else if (buttons[i][j].a == 1)
                                white++;
                        }

                    if (black > white) {
                        Toast.makeText(this, "Black wins", Toast.LENGTH_LONG).show();
                        super.setTitle("Black:"+black+"    "+"White:"+white);
                        return;
                    } else

                    {
                        Toast.makeText(this, "White wins", Toast.LENGTH_LONG).show();
                        super.setTitle("Black:"+black+"    "+"White:"+white);

                        return;
                    }


                }


                super.setTitle(crossturn ? "Black's turn" : "White's turn");
            }
        }
    }


}











