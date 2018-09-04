package cn.mklaus.demo.conf;


import cn.mklaus.demo.entity.User;

/**
 * @author Mklaus
 * @date 2018-06-20 下午5:39
 */
public class UserHolder {

    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static User get() {
        return USER_THREAD_LOCAL.get();
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }

}
