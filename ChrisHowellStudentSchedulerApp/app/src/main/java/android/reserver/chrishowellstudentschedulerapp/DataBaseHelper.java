package android.reserver.chrishowellstudentschedulerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String TERM_TABLE = "Term_Table";
    public static final String TERM_ID = "term_id";
    public static final String TERM_TITLE = "Term_title";
    public static final String TERM_START_DATE = "Term_startDate";
    public static final String TERM_END_DATE = "Term_endDate";

    public static final String COURSE_TABLE = "Course_Table";
    public static final String COURSE_TERM_ID = "Course_Term_Id";
    public static final String COURSE_ID = "Course_ID";
    public static final String COURSE_TITLE = "Course_Title";

    public static final String COURSE_START_DATE = "Course_Start_Date";
    public static final String COURSE_END_DATE = "Course_End_Date";
    public static final String COURSE_STATUS = "Course_Status";
    public static final String COURSE_INSTRUCTOR_NAME = "Course_Instructor_Name";
    public static final String COURSE_INSTRUCTOR_PHONE = "Course_Instructor_Phone";
    public static final String COURSE_INSTRUCTOR_EMAIL = "Course_Instructor_Email";
    public static final String COURSE_NOTE = "Course_Note";

    public static final String ASSESSMENT_TABLE = "Assessment_Table";
    public static final String ASSESSMENT_ID = "Assessment_Id";
    public static final String ASSESSMENT_COURSE_ID = "Assessment_Course_Id";
    public static final String ASSESSMENT_TYPE = "Assessment_Type";
    public static final String ASSESSMENT_NAME = "Assessment_Name";
    public static final String ASSESSMENT_START_DATE = "Assessment_Start_Date";
    public static final String ASSESSMENT_END_DATE = "Assessment_End_Date";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "schedular.db", null, 1);
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //called the first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create the Term Table in the Database db
        String createTermTableStatement = "CREATE TABLE " + TERM_TABLE + " ( " +
                TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TERM_TITLE + " TEXT , " +
                TERM_START_DATE + " TEXT , " +
                TERM_END_DATE + " TEXT );";
        db.execSQL(createTermTableStatement);

        //Create the Course Table in the Database db
        String createTCourseTableStatement = "CREATE TABLE " +COURSE_TABLE+" ( " +
                COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COURSE_TERM_ID + " INTEGER REFERENCES " +TERM_TABLE+ "(" +TERM_ID+ ") NOT NULL," +
                COURSE_TITLE + " TEXT, " +
                COURSE_START_DATE + " TEXT," +
                COURSE_END_DATE + " TEXT," +
                COURSE_STATUS + " TEXT," +
                COURSE_INSTRUCTOR_NAME + " TEXT," +
                COURSE_INSTRUCTOR_PHONE + " TEXT," +
                COURSE_INSTRUCTOR_EMAIL + " TEXT," +
                COURSE_NOTE + " TEXT);";
        db.execSQL(createTCourseTableStatement);

        //create the Assesment Table in the Database db
        String createTAssessmentTableStatement = "CREATE TABLE " +ASSESSMENT_TABLE+" ( " +
                ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ASSESSMENT_COURSE_ID + " INTEGER REFERENCES " +COURSE_TABLE+ "(" +COURSE_ID+ ") NOT NULL," +
                ASSESSMENT_TYPE + " TEXT," +
                ASSESSMENT_NAME + " TEXT," +
                ASSESSMENT_START_DATE + " TEXT," +
                ASSESSMENT_END_DATE + " TEXT);";
        db.execSQL(createTAssessmentTableStatement);
    }

    //called when the version of database changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addTerm(Context context, Term term,String previousActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TERM_TITLE, term.getTitle());
        cv.put(TERM_START_DATE, term.getStartDate());
        cv.put(TERM_END_DATE, term.getEndDate());

        //Checks which was the previous activity to determine if it should add or edit existing
        if (previousActivity.equals("MainActivity")) {
            // insert new row into the table
            long result = db.insert(TERM_TABLE, null, cv);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else if (previousActivity.equals("TermView")) {
            // update existing row in the table
            int result = db.update(TERM_TABLE, cv, TERM_ID + " = ?",
                    new String[]{String.valueOf(term.getId())});
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }


    }
    public boolean addCourse(Context context, Course course,String previousActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String course_type = course.getStatus().toString();

        //optional note is empty or null code below inserts none as the value
        String noteStr = course.getOptionalNote();
        if(noteStr =="" || noteStr == null){
            noteStr = "none";
        }
        cv.put(COURSE_TERM_ID,course.getTerm_id());
        cv.put(COURSE_TITLE, course.getTitle());
        cv.put(COURSE_START_DATE, course.getStartDate());
        cv.put(COURSE_END_DATE, course.getEndDate());
        cv.put(COURSE_STATUS, course_type);
        cv.put(COURSE_INSTRUCTOR_NAME, course.getInstructorName());
        cv.put(COURSE_INSTRUCTOR_PHONE, course.getInstructorPhone() );
        cv.put(COURSE_INSTRUCTOR_EMAIL,course.getInstructorEmail() );
        cv.put(COURSE_NOTE,noteStr);

        if (previousActivity.equals("TermView")) {
            // insert new row into the table
            long result = db.insert(COURSE_TABLE, null, cv);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else if (previousActivity.equals("CourseView")) {
            // update existing row in the table
            int result = db.update(COURSE_TABLE, cv, COURSE_ID + " = ?",
                    new String[]{String.valueOf(course.getId())});
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }
    public boolean addAssessment(Context context, Assessment assessment,String previousActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String assessment_type = assessment.getAssessmentType().toString();
        values.put(ASSESSMENT_COURSE_ID, assessment.getCourse_Id());
        values.put(ASSESSMENT_TYPE, assessment_type);
        values.put(ASSESSMENT_NAME, assessment.getTitle());
        values.put(ASSESSMENT_START_DATE, assessment.getStartDate());
        values.put(ASSESSMENT_END_DATE, assessment.getEndDate());

        if (previousActivity.equals("CourseView")) {
            // insert new row into the table
            long result = db.insert(ASSESSMENT_TABLE, null, values);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else if (previousActivity.equals("AssessmentView")) {
            // update existing row in the table
            int result = db.update(ASSESSMENT_TABLE, values, ASSESSMENT_ID + " = ?",
                    new String[]{String.valueOf(assessment.getId())});
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    //Method gets all terms to be populated into MainActivity
    public List<Term> getAllTerms() {

        List<Term> termlist = new ArrayList<>();

        String queryString = "Select * From "+ TERM_TABLE+";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String start = cursor.getString(2);
                    String end = cursor.getString(3);
                    Term newTerm = new Term(id,title,start,end);
                    termlist.add(newTerm);
                }while(cursor.moveToNext());
            }
            else{
                //does nothing to termlist and termlist will
            }
            cursor.close();
            db.close();


        return termlist;
    }
    //Method gets all the courses for a specific Term
    public List<Course> getAllCourses(int term_id){
        List<Course> courselist = new ArrayList<>();

        // using placeholder to help protect against SQL injection attack
        String queryString = "Select * From " + COURSE_TABLE + " WHERE " + COURSE_TERM_ID + " = ?;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] { String.valueOf(term_id) });

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                term_id = cursor.getInt(1);
                String title = cursor.getString(2);
                String start = cursor.getString(3);
                String end = cursor.getString(4);
                String status = cursor.getString(5);
                Course.StatusEnum statusEnum = Course.StatusEnum.valueOf(status);
                String ins_name = cursor.getString(6);
                String ins_phone = cursor.getString(7);
                String ins_email = cursor.getString(8);
                String note = cursor.getString(9);
                Course newCourse = new Course(id,term_id,title,start,end,statusEnum,ins_name,ins_phone,ins_email,note);
                courselist.add(newCourse);
            }while(cursor.moveToNext());
        }
        else{
            //does nothing to termlist and termlist will
        }
        cursor.close();
        db.close();
        return courselist;
    }
    //Method gets all the Assessments for a specific Course
    public List<Assessment> getAllAssessments(int course_id){

        List<Assessment> assessment_list = new ArrayList<>();

        // using placeholder to protect against SQL injection attack
        String queryString = "Select * From " + ASSESSMENT_TABLE + " WHERE " + ASSESSMENT_COURSE_ID + " = ?;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] { String.valueOf(course_id) });

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                course_id = cursor.getInt(1);
                String type = cursor.getString(2);
                Assessment.AssessmentType typeEnum = Assessment.AssessmentType.valueOf(type);
                String name = cursor.getString(3);
                String sd = cursor.getString(4);
                String ed = cursor.getString(5);
                Assessment assesment = new Assessment(id,course_id,name,sd,ed,typeEnum);
                assessment_list.add(assesment);
            }while(cursor.moveToNext());
        }
        else{
            //does nothing to termlist and termlist will
        }
        cursor.close();
        db.close();
        return assessment_list;
    }
    public String getNote(int courseID)
    {
        String note;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM "+ COURSE_TABLE+ " WHERE "+COURSE_ID+ " = ?;";
        Cursor cursor = db.rawQuery(queryString,new String[] { String.valueOf(courseID)});
        if (cursor.moveToFirst()) {
            note = cursor.getString(9);
        }
        else{
            note = "None";
        }
        return note;
    }
    public boolean addNote(int courseID,String note){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_NOTE, note);
        int result= db.update(COURSE_TABLE, values, COURSE_ID + " = ?", new String[] { String.valueOf(courseID) });
        db.close();
        return result > 0;
    }
    public Term getTerm(int term_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM "+ TERM_TABLE+ " WHERE "+TERM_ID+ " = ?;";
        Cursor cursor = db.rawQuery(queryString,new String[] { String.valueOf(term_id) });
        Term term = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String start = cursor.getString(2);
            String end = cursor.getString(3);
            term = new Term(id,title,start,end);
        }
        cursor.close();
        db.close();
        return term;
    }
    public Course getCourse(int courseID){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM "+ COURSE_TABLE+ " WHERE "+COURSE_ID+ " = ?;";
        Cursor cursor = db.rawQuery(queryString,new String[] { String.valueOf(courseID) });
        Course course = null;
        System.out.println(cursor.moveToFirst());
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int term_id = cursor.getInt(1);
            String title = cursor.getString(2);
            String start = cursor.getString(3);
            String end = cursor.getString(4);
            String status = cursor.getString(5);
            Course.StatusEnum statusEnum = Course.StatusEnum.valueOf(status);
            String ins_name = cursor.getString(6);
            String ins_phone = cursor.getString(7);
            String ins_email = cursor.getString(8);
            String note = cursor.getString(9);
            course = new Course(id,term_id,title,start,end,statusEnum,ins_name,ins_phone,ins_email,note);
        }
        cursor.close();
        db.close();
        return course;
    }
    public Assessment getAssessment(int assessmentID){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM "+ ASSESSMENT_TABLE+ " WHERE "+ASSESSMENT_ID+ " = ?;";
        Cursor cursor = db.rawQuery(queryString,new String[] { String.valueOf(assessmentID) });
        Assessment assesment = null;
        if (cursor.moveToFirst()) {
            assessmentID = cursor.getInt(0);
            int course_id = cursor.getInt(1);
            String type = cursor.getString(2);
            Assessment.AssessmentType typeEnum = Assessment.AssessmentType.valueOf(type);
            String name = cursor.getString(3);
            String sd = cursor.getString(4);
            String ed = cursor.getString(5);
            assesment = new Assessment(assessmentID,course_id,name,sd,ed,typeEnum);
        }
        cursor.close();
        db.close();
        return assesment;
    }
    public boolean deleteTerm(Term term)
    {

        if(!(validateTermDelete(term))){
            System.out.println("delete term is returning false");
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStr = "DELETE FROM " +TERM_TABLE+" WHERE "+TERM_ID+ " = " + term.getId();
        Cursor cursor = db.rawQuery(queryStr,null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
            return true;


    }
    public boolean deleteCourse(Course course)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Assessment> assessmentList = new ArrayList<>();
        
        //checks to see if assessment list is empty in this course, if not it deletes them
        if(!(assessmentList.isEmpty())){
            for(Assessment assessment:assessmentList){
                deleteAssessment(assessment);
            }
        }
        // creates string to execute deletion of course
        String queryStr = "DELETE FROM " +COURSE_TABLE+" WHERE "+COURSE_ID+ " = " + course.getId();
        Cursor cursor = db.rawQuery(queryStr,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }

        
    }
    public boolean deleteAssessment(Assessment assessment)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStr = "DELETE FROM " +ASSESSMENT_TABLE+" WHERE "+ASSESSMENT_ID+ " = " + assessment.getId();
        Cursor cursor = db.rawQuery(queryStr,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validateTermDelete(Term term)
    {
        //Method to check to see if there are any courses associated with a specific Term
        SQLiteDatabase db = this.getReadableDatabase();
        int termID = term.getId();
        String queryStr = "SELECT * FROM " + COURSE_TABLE + " WHERE " + COURSE_TERM_ID + " = " + termID;
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {

            return false;
        } else {

            return true;
        }
    }


}
