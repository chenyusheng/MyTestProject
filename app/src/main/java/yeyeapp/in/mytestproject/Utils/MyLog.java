package yeyeapp.in.mytestproject.Utils;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

public class MyLog {

    private static String mTag = "shengBro";
    private static boolean mIsEnableLog = true;//调试开关


    public static void setAdLogEnabled(boolean flag) {
        mIsEnableLog = flag;
    }

    public static void setAdLogTag(String tag) {
        mTag = tag;
    }

    private static void printLineInfo(String tag) {
        if (mIsEnableLog) {
            try {
                StackTraceElement[] elements = Thread.currentThread()
                        .getStackTrace();
                if (elements.length > 5) {

                    StackTraceElement element = elements[5];

                    @SuppressLint("DefaultLocale") String txt = String
                            .format("Time:[ %d ] Thread:[ %d ] Method:( %s ) FileName:[ %s ] Line:( %d )",
                                    System.currentTimeMillis(), Thread
                                            .currentThread().getId(), element
                                            .getMethodName(), element
                                            .getFileName(), element
                                            .getLineNumber());
                    MyLog.ti(tag, txt);
                }
            } catch (Throwable e) {
            }
        }
    }

    // Info
    public static void ti(String tag, String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.INFO, tag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void ti(String tag, String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.INFO, tag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    public static void i(String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.INFO, mTag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void i(Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.INFO, mTag, "", tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void i(String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.INFO, mTag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    // Error
    public static void te(String tag, String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.ERROR, tag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void te(String tag, String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.ERROR, tag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }


    public static void e(String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {

                taglog(Log.ERROR, mTag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void e(Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.ERROR, mTag, "", tr);

            } catch (Throwable e) {
            }
        }


    }

    public static void e(String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.ERROR, mTag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    //默认的 log，直接使用 e 等级
    public static void log(String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {

                taglog(Log.ERROR, mTag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void log(Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.ERROR, mTag, "", tr);

            } catch (Throwable e) {
            }
        }


    }

    public static void log(String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.ERROR, mTag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    // warn
    public static void tw(String tag, String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.WARN, tag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void tw(String tag, String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.WARN, tag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    public static void w(String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.WARN, mTag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void w(Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.WARN, mTag, "", tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void w(String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.WARN, mTag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    // debug
    public static void td(String tag, String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.DEBUG, tag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void td(String tag, String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.DEBUG, tag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    public static void d(String msg, Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.DEBUG, mTag, msg, tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void d(Throwable tr) {
        if (mIsEnableLog) {
            try {
                taglog(Log.DEBUG, mTag, "", tr);
            } catch (Throwable e) {
            }
        }
    }

    public static void d(String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                taglog(Log.DEBUG, mTag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    public static void debugTagLog(int level, String tag, String fmt,
                                   Object... args) {
        if (mIsEnableLog) {
            try {
                printLineInfo(tag);
                taglog(level, tag, fmt, args);
            } catch (Throwable e) {
            }
        }
    }

    public static void taglog(int level, String tag, String fmt, Object... args) {
        if (mIsEnableLog) {
            try {
                String msg = String.format(fmt, args);
                switch (level) {
                    case Log.DEBUG:
                        Logger.d(msg, args);
                        //log.d(tag, msg);
                        break;
                    case Log.ERROR:
                        Logger.e(msg, args);
                        //log.e(tag, msg);
                        break;
                    case Log.INFO:
                        Logger.i(msg, args);
                        //log.i(tag, msg);
                        break;
                    case Log.WARN:
                        Logger.w(msg, args);
                        //log.w(tag, msg);
                        break;
                    default:
                        Logger.v(msg, args);
                        //log.v(tag, msg);
                        break;
                }
            } catch (Throwable e) {
            }
        }
    }

    public static void debugTagLog(int level, String tag, String msg,
                                   Throwable err) {
        if (mIsEnableLog) {
            try {
                printLineInfo(tag);
                taglog(level, tag, msg, err);
            } catch (Exception e) {
            }
        }
    }

    public static void taglog(int level, String tag, String msg, Throwable err) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mIsEnableLog) {
            try {
                switch (level) {
                    case Log.DEBUG:
                        Logger.d(msg, err);
//					log.d(tag, msg, err);
                        break;
                    case Log.ERROR:
                        Logger.e(msg, err);
//					log.e(tag, msg, err);
                        break;
                    case Log.INFO:
                        Logger.i(msg, err);
//					log.i(tag, msg, err);
                        break;
                    case Log.WARN:
                        Logger.w(msg, err);
//					log.w(tag, msg, err);
                        break;
                    default:
                        Logger.v(msg, err);
//					log.v(tag, msg, err);
                        break;
                }
            } catch (Throwable e) {
            }
        }
    }
}
