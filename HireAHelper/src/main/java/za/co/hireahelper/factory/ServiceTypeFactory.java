// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.ServiceType;

public class ServiceTypeFactory {

    public static ServiceType createServiceType(String typeId, String typeName) {
        if (typeId == null || typeId.isEmpty()
                || typeName == null || typeName.isEmpty()) {
            return null;
        }

        return new ServiceType.Builder()
                .setTypeId(typeId)
                .setTypeName(typeName)
                .build();
    }
}
