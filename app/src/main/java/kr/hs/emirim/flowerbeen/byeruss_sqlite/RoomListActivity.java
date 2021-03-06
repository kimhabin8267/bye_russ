package kr.hs.emirim.flowerbeen.byeruss_sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;


public class RoomListActivity extends AppCompatActivity{

    private final String TAG = "RoomListActivity";
    private String DB_PATH =  "/data/data/kr.hs.emirim.flowerbeen.byeruss/byeruss_room.db";

    ListView room_list_view;

    MyDBHandler myDBHandler;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    //cursor adapter는 커서로 읽은 정보를 list로 만들어 주는 역할을 한다.
    //따라서 DB에서 읽은 정보를 listview 형태로 보여줄때 사용한다.
    //Simple Cursor Adapter : cursor adatper중에 가장 간단한 adapter 이다.
    //Simple cursor adatper는 cursor에 있는 정보를 textView나 imageView로 보여줄때 사용한다.

    private String roomName, roomTime, roomPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        room_list_view = findViewById(R.id.room_list_view);
        room_list_view.setOnItemLongClickListener(mLongClickListener);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();

        String sql = "SELECT * FROM byeruss_make_room;";
        //cursor = myDBHandler.select();
        cursor = sqLiteDatabase.rawQuery(sql, null);
        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_room,
                cursor, new String[]{"roomName", "roomTime", "roomPlace"}, new int[]{R.id.roomNameTextView, R.id.roomTimeTextView, R.id.roomPlaceTextView}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        room_list_view.setAdapter(simpleCursorAdapter);

    }
    AdapterView.OnItemLongClickListener mLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            cursor.moveToPosition(position);
            Log.d(TAG, "roomName : " + cursor.getString(0));
            myDBHandler.delete(cursor.getString(0));

            cursor = myDBHandler.select();  // DB 새로 가져오기
            simpleCursorAdapter.changeCursor(cursor); // Adapter에 변경된 Cursor 설정하기
            simpleCursorAdapter.notifyDataSetChanged(); // 업데이트 하기

            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDBHandler.close();
    }

}