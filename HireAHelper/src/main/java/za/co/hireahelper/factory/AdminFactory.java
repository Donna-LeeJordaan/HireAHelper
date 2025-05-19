//Ameeruddin Arai 230190839

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.util.Helper;

public class AdminFactory {

    public static Admin createAdmin(String userId, String name, String email, String password, String mobileNumber) {
        if (Helper.isNullOrEmpty(userId) ||
                Helper.isNullOrEmpty(name) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password) ||
                Helper.isNullOrEmpty(mobileNumber)) {
            return null;
        }

        return new Admin.Builder()
                .setUserId(userId)
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setMobileNumber(mobileNumber)
                .build();
    }
}
