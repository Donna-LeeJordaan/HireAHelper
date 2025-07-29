// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.util.Helper;

public class ServiceTypeFactory {
    public static ServiceType createServiceType(String typeId, String typeName) {
        if (Helper.isNullOrEmpty(typeId) || Helper.isNullOrEmpty(typeName)) {
            return null;
        }

        return new ServiceType.Builder()
                .setTypeId(typeId)
                .setTypeName(typeName)
                .build();
    }
}
