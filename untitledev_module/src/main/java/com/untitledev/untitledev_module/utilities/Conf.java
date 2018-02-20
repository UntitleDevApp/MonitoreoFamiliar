package com.untitledev.untitledev_module.utilities;

/**
 * Created by Cipriano on 2/19/2018.
 */

public enum Conf {
    //#Archivo de configuración de parametros...
    //##########################################################
    //#PARAMETERS
    //#HTTP Connection Config
    http_connectiontimeout("50000"),
    http_sotimeout("3000"),
    http_readtimeout("50000"),

    //#Host
    http_host("http://iaher.com.mx/monitoreofamiliar/api/"),

    //#Entities
    http_users("users"),
    http_contacts("contacts"),
    http_monitoring_history("monitoring_history"),
    http_monitoring_permissions("monitoring_permissions"),
    http_users_locations("users_locations"),

    //#Methods
    //-----users----
    http_users_create("/create"),
    http_users_read("/read"),
    http_users_update("/update"),
    http_users_delete("/delete"),
    http_users_login("/login"),
    http_users_logout("logout"),

    //-----contacts--------------
    http_contacts_create("/create"),
    http_contacts_read("/read"),
    http_contacts_update("/update"),
    http_contacts_delete("/delete"),

    //-----monitoring_history----
    http_monitoring_history_create("/create"),
    http_monitoring_history_read("/read"),
    http_monitoring_history_update("/update"),
    http_monitoring_history_delete("/delete"),

    //------monitoring_permissions----
    http_monitoring_permissions_create("/create"),
    http_monitoring_permissions_history_read("/read"),
    http_monitoring_permissions_history_update("/update"),
    http_monitoring_permissions_history_delete("/delete"),

    //----users_locations-----
    http_users_locations_create("/create"),
    http_users_locations_read("/read"),
    http_users_locations_update("/update"),
    http_users_locations_delete("/delete"),


    //#Port
    http_port("1026"),
    http_portnotify("5050"),


    //#notify
    http_notify("notify"),

    //#API Version
    http_apiversion("v2"),

    //#attrs
    http_attrs("attrs");


    private String propiedad;

    private Conf(String propiedad) {
        this.setPropiedad(propiedad);
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

   /*public static void main(String[] args) {
       System.out.println(Util.http_attrs.getPropiedad());
    }*/
}
