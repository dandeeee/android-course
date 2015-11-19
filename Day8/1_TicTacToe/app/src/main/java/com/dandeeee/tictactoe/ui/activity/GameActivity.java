package com.dandeeee.tictactoe.ui.activity;

import com.dandeeee.tictactoe.model.GameLogic;
import com.dandeeee.tictactoe.ui.component.BoardView;
import com.dandeeee.tictactoe.Constants;
import com.dandeeee.tictactoe.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;


public class GameActivity extends Activity {

	// Indicates if game is currently over or not
    private boolean mIsGameOver = false;

    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;


    private char mGoFirst = GameLogic.HUMAN_PLAYER; // Whose turn to go first
    private char mTurn = GameLogic.COMPUTER_PLAYER; // Whose turn is it

    private int mStatHumanWins = 0;
    private int mStatComputerWins = 0;
    private int mStatTies = 0;

    private GameLogic mGameLogic; // Represents the internal state of the game

    private BoardView mBoardView; // Represents the game board
    
    private TextView mInfoTextView;
    private TextView mHumanScoreTextView;
    private TextView mComputerScoreTextView;
    private TextView mTieScoreTextView;

    private SharedPreferences mPrefs;

    private boolean mIsSoundOn;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameLogic = new GameLogic();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGameLogic);

        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanScoreTextView = (TextView) findViewById(R.id.player_score);
        mComputerScoreTextView = (TextView) findViewById(R.id.computer_score);
        mTieScoreTextView = (TextView) findViewById(R.id.tie_score);

        // Restore the scores from the persistent preference data source
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mIsSoundOn = mPrefs.getBoolean(Constants.SOUND_PREFERENCE_KEY, true);
        mStatHumanWins = mPrefs.getInt(Constants.STAT_HUMAN_WINS_AMOUNT, 0);
        mStatComputerWins = mPrefs.getInt(Constants.STAT_COMP_WINS_AMOUNT, 0);
        mStatTies = mPrefs.getInt(Constants.STAT_TIES_AMOUNT, 0);
        mBoardView.setBoardColor(mPrefs.getInt(Constants.BOARD_COLOR_PREFERENCE_KEY, Color.GRAY));
        String difficultyLevel = mPrefs.getString(Constants.DIFFICULTY_PREFERENCE_KEY,
                getResources().getString(R.string.difficulty_harder));

        if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
            mGameLogic.setDifficultyLevel(GameLogic.DifficultyLevel.Easy);
        else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
            mGameLogic.setDifficultyLevel(GameLogic.DifficultyLevel.Harder);
        else
            mGameLogic.setDifficultyLevel(GameLogic.DifficultyLevel.Expert);

        if (savedInstanceState == null) {
            startNewGame();
        } else {
            // Restore the game's state
            // The same thing can be accomplished with onRestoreInstanceState
            mGameLogic.setBoardState(savedInstanceState.getCharArray(Constants.BOARD_STATE_KEY));
            mIsGameOver = savedInstanceState.getBoolean(Constants.IS_GAME_OVER_KEY);
            mInfoTextView.setText(savedInstanceState.getCharSequence(Constants.INFO_TEXT_VIEW_KEY));
            mTurn = savedInstanceState.getChar(Constants.TURN_KEY);
            mGoFirst = savedInstanceState.getChar(Constants.GO_FIRST_KEY);

            // If it's the computer's turn, the previous turn did not take, so go again
            if (!mIsGameOver && mTurn == GameLogic.COMPUTER_PLAYER) {
                int move = mGameLogic.getComputerMove();
                setMove(GameLogic.COMPUTER_PLAYER, move);
            }
        }

        displayScores();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_x);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_o);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save the current score, but not the state of the current game
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt(Constants.STAT_HUMAN_WINS_AMOUNT, mStatHumanWins);
        ed.putInt(Constants.STAT_COMP_WINS_AMOUNT, mStatComputerWins);
        ed.putInt(Constants.STAT_TIES_AMOUNT, mStatTies);
        ed.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharArray(Constants.BOARD_STATE_KEY, mGameLogic.getBoardState());
        outState.putBoolean(Constants.IS_GAME_OVER_KEY, mIsGameOver);
        outState.putCharSequence(Constants.INFO_TEXT_VIEW_KEY, mInfoTextView.getText());
        outState.putChar(Constants.GO_FIRST_KEY, mGoFirst);
        outState.putChar(Constants.TURN_KEY, mTurn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (id) {
            case Constants.DIALOG_QUIT:
                // Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
                
            case Constants.DIALOG_ABOUT:
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.dialog_about, null);

                builder.setView(layout);
                builder.setPositiveButton("OK", null);
                dialog = builder.create();
                break;
        }

        return dialog;
    }


    // Handles menu item selections
    @SuppressWarnings("deprecation")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_CANCELED);
                return true;
            case R.id.reset_scores:
                mStatHumanWins = 0;
                mStatComputerWins = 0;
                mStatTies = 0;
                displayScores();
                return true;
            case R.id.about:
                showDialog(Constants.DIALOG_ABOUT);
                return true;
            case R.id.quit:
                showDialog(Constants.DIALOG_QUIT);
                return true;


        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // See if Back button was pressed on Settings activity
        if (requestCode == RESULT_CANCELED) {
            // Apply potentially new settings

            mIsSoundOn = mPrefs.getBoolean(Constants.SOUND_PREFERENCE_KEY, true);

            String difficultyLevel = mPrefs.getString(Constants.DIFFICULTY_PREFERENCE_KEY,
                    getResources().getString(R.string.difficulty_harder));
            if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
                mGameLogic.setDifficultyLevel(GameLogic.DifficultyLevel.Easy);
            else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
                mGameLogic.setDifficultyLevel(GameLogic.DifficultyLevel.Harder);
            else
                mGameLogic.setDifficultyLevel(GameLogic.DifficultyLevel.Expert);

            String goesFirst = mPrefs.getString(Constants.GOES_FIRST_PREFERENCE_KEY, "Alternate");
            if (!goesFirst.equals("Alternate")) {
                // See if any moves have been made.  If not, start a new game
                // which will use the selected setting
                for (int i = 0; i < 8; i++)
                    if (mGameLogic.getBoardOccupant(i) != GameLogic.OPEN_SPOT)
                        return;

                // All spots must be open
                startNewGame();
            }

            mBoardView.setBoardColor(mPrefs.getInt(Constants.BOARD_COLOR_PREFERENCE_KEY, Color.GRAY));
        }
    }

    // Show the scores
    private void displayScores() {
        mHumanScoreTextView.setText(Integer.toString(mStatHumanWins));
        mComputerScoreTextView.setText(Integer.toString(mStatComputerWins));
        mTieScoreTextView.setText(Integer.toString(mStatTies));
    }

    // Set up the game board.
    private void startNewGame() {

        mGameLogic.clearBoard();
        mBoardView.invalidate();   // Redraw the board

        // Determine who should go first based on settings
        String goesFirst = mPrefs.getString(Constants.GOES_FIRST_PREFERENCE_KEY, "Alternate");

        if (goesFirst.equals("Alternate")) {
            // Alternate who goes first
            if (mGoFirst == GameLogic.COMPUTER_PLAYER) {
                mGoFirst = GameLogic.HUMAN_PLAYER;
                mTurn = GameLogic.COMPUTER_PLAYER;
            } else {
                mGoFirst = GameLogic.COMPUTER_PLAYER;
                mTurn = GameLogic.HUMAN_PLAYER;
            }
        } else if (goesFirst.equals("Human"))
            mTurn = GameLogic.HUMAN_PLAYER;
        else
            mTurn = GameLogic.COMPUTER_PLAYER;

        // Start the game
        if (mTurn == GameLogic.COMPUTER_PLAYER) {
            mInfoTextView.setText(R.string.first_computer);
            int move = mGameLogic.getComputerMove();
            setMove(GameLogic.COMPUTER_PLAYER, move);
        } else
            mInfoTextView.setText(R.string.first_human);

        mIsGameOver = false;
    }

    // Make a move
    private boolean setMove(char player, int location) {

        if (player == GameLogic.COMPUTER_PLAYER) {
            // Make the computer move after a delay of 1 second
            final int loc = location;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    mGameLogic.setMove(GameLogic.COMPUTER_PLAYER, loc);
                    mBoardView.invalidate();   // Redraw the board

                    try {
                        if (mIsSoundOn)
                            mComputerMediaPlayer.start();
                    } catch (IllegalStateException e) {
                    }
                    ;  // Happens if orientation changed before playing

                    int winner = mGameLogic.checkForWinner();
                    if (winner == GameLogic.NO_WIN) {
                        mTurn = GameLogic.HUMAN_PLAYER;
                        mInfoTextView.setText(R.string.turn_human);
                    } else
                        endGame(winner);
                }
            }, 1000);

            return true;
        } else if (mGameLogic.setMove(GameLogic.HUMAN_PLAYER, location)) {
            mTurn = GameLogic.COMPUTER_PLAYER;
            mBoardView.invalidate();   // Redraw the board
            if (mIsSoundOn)
                mHumanMediaPlayer.start();
            return true;
        }

        return false;
    }

    // Game is over logic
    private void endGame(int winner) {
        if (winner == GameLogic.TIE_WIN) {
            mStatTies++;
            mTieScoreTextView.setText(Integer.toString(mStatTies));
            mInfoTextView.setText(R.string.result_tie);
        } else if (winner == GameLogic.HUMAN_WIN) {
            mStatHumanWins++;
            mHumanScoreTextView.setText(Integer.toString(mStatHumanWins));
            String defaultMessage = getResources().getString(R.string.result_human_wins);
            mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
        } else if (winner == GameLogic.COMPUTER_WIN) {
            mStatComputerWins++;
            mComputerScoreTextView.setText(Integer.toString(mStatComputerWins));
            mInfoTextView.setText(R.string.result_computer_wins);
        }

        mIsGameOver = true;
    }

    // Listen for touches on the board
    private OnTouchListener mTouchListener = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {

            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if (!mIsGameOver && mTurn == GameLogic.HUMAN_PLAYER &&
                    setMove(GameLogic.HUMAN_PLAYER, pos)) {

                // If no winner yet, let the computer make a move
                int winner = mGameLogic.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGameLogic.getComputerMove();
                    setMove(GameLogic.COMPUTER_PLAYER, move);
                } else
                    endGame(winner);
            }

            // So we aren't notified of continued events when finger is moved
            return false;
        }
    };
}