package cycu.nclab.moocs2018.room;

import androidx.room.TypeConverter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Converters {

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    @TypeConverter
    public static String calendarToToday(Calendar c) {
        return c == null ? null : df.format(c.getTime());
    }
}