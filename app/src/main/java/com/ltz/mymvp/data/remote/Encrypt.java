package com.ltz.mymvp.data.remote;

public class Encrypt {


    public EncryptData getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(EncryptData encrypt) {
        this.encrypt = encrypt;
    }

    private EncryptData encrypt;

    public static class EncryptData{
        private String field_name;

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public String getField_value() {
            return field_value;
        }

        public void setField_value(String field_value) {
            this.field_value = field_value;
        }

        public String getPublic_key() {
            return public_key;
        }

        public void setPublic_key(String public_key) {
            this.public_key = public_key;
        }

        private String field_value;
        private String public_key;
    }
}
