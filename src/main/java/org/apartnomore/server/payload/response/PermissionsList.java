package org.apartnomore.server.payload.response;

import org.apartnomore.server.models.EPermission;

import java.util.HashSet;
import java.util.Set;

public class PermissionsList {
    Set<EPermission> permissionSet;

    public PermissionsList() {
        this.permissionSet = new HashSet<EPermission>();

    }


    public Set<EPermission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<EPermission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public void addPermission(EPermission permission) {
        this.permissionSet.add(permission);
    }
}
