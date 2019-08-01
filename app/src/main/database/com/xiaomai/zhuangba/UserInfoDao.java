package com.xiaomai.zhuangba;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xiaomai.zhuangba.data.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PhoneNumber = new Property(1, String.class, "phoneNumber", false, "PHONE_NUMBER");
        public final static Property UserText = new Property(2, String.class, "userText", false, "USER_TEXT");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property LockFlag = new Property(4, int.class, "lockFlag", false, "LOCK_FLAG");
        public final static Property InvitationCode = new Property(5, String.class, "invitationCode", false, "INVITATION_CODE");
        public final static Property Token = new Property(6, String.class, "token", false, "TOKEN");
        public final static Property Role = new Property(7, String.class, "role", false, "ROLE");
        public final static Property RegistrationTime = new Property(8, String.class, "registrationTime", false, "REGISTRATION_TIME");
        public final static Property IdentityCard = new Property(9, String.class, "identityCard", false, "IDENTITY_CARD");
        public final static Property ValidityDataStart = new Property(10, String.class, "validityDataStart", false, "VALIDITY_DATA_START");
        public final static Property ValidityDataEnd = new Property(11, String.class, "validityDataEnd", false, "VALIDITY_DATA_END");
        public final static Property IdCardFrontPhoto = new Property(12, String.class, "idCardFrontPhoto", false, "ID_CARD_FRONT_PHOTO");
        public final static Property IdCardBackPhoto = new Property(13, String.class, "idCardBackPhoto", false, "ID_CARD_BACK_PHOTO");
        public final static Property ValidityData = new Property(14, String.class, "validityData", false, "VALIDITY_DATA");
        public final static Property BareHeadedPhotoUrl = new Property(15, String.class, "bareHeadedPhotoUrl", false, "BARE_HEADED_PHOTO_URL");
        public final static Property AuthenticationStatue = new Property(16, int.class, "authenticationStatue", false, "AUTHENTICATION_STATUE");
        public final static Property AuthenticationTime = new Property(17, String.class, "authenticationTime", false, "AUTHENTICATION_TIME");
        public final static Property Address = new Property(18, String.class, "address", false, "ADDRESS");
        public final static Property Longitude = new Property(19, double.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(20, double.class, "latitude", false, "LATITUDE");
        public final static Property BusinessLicense = new Property(21, String.class, "businessLicense", false, "BUSINESS_LICENSE");
        public final static Property EmergencyContact = new Property(22, String.class, "emergencyContact", false, "EMERGENCY_CONTACT");
        public final static Property ContactAddress = new Property(23, String.class, "contactAddress", false, "CONTACT_ADDRESS");
        public final static Property StartFlag = new Property(24, int.class, "startFlag", false, "START_FLAG");
        public final static Property PayFlag = new Property(25, int.class, "payFlag", false, "PAY_FLAG");
        public final static Property MasterRankId = new Property(26, String.class, "masterRankId", false, "MASTER_RANK_ID");
        public final static Property MasterRankName = new Property(27, String.class, "masterRankName", false, "MASTER_RANK_NAME");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PHONE_NUMBER\" TEXT," + // 1: phoneNumber
                "\"USER_TEXT\" TEXT," + // 2: userText
                "\"PASSWORD\" TEXT," + // 3: password
                "\"LOCK_FLAG\" INTEGER NOT NULL ," + // 4: lockFlag
                "\"INVITATION_CODE\" TEXT," + // 5: invitationCode
                "\"TOKEN\" TEXT," + // 6: token
                "\"ROLE\" TEXT," + // 7: role
                "\"REGISTRATION_TIME\" TEXT," + // 8: registrationTime
                "\"IDENTITY_CARD\" TEXT," + // 9: identityCard
                "\"VALIDITY_DATA_START\" TEXT," + // 10: validityDataStart
                "\"VALIDITY_DATA_END\" TEXT," + // 11: validityDataEnd
                "\"ID_CARD_FRONT_PHOTO\" TEXT," + // 12: idCardFrontPhoto
                "\"ID_CARD_BACK_PHOTO\" TEXT," + // 13: idCardBackPhoto
                "\"VALIDITY_DATA\" TEXT," + // 14: validityData
                "\"BARE_HEADED_PHOTO_URL\" TEXT," + // 15: bareHeadedPhotoUrl
                "\"AUTHENTICATION_STATUE\" INTEGER NOT NULL ," + // 16: authenticationStatue
                "\"AUTHENTICATION_TIME\" TEXT," + // 17: authenticationTime
                "\"ADDRESS\" TEXT," + // 18: address
                "\"LONGITUDE\" REAL NOT NULL ," + // 19: longitude
                "\"LATITUDE\" REAL NOT NULL ," + // 20: latitude
                "\"BUSINESS_LICENSE\" TEXT," + // 21: businessLicense
                "\"EMERGENCY_CONTACT\" TEXT," + // 22: emergencyContact
                "\"CONTACT_ADDRESS\" TEXT," + // 23: contactAddress
                "\"START_FLAG\" INTEGER NOT NULL ," + // 24: startFlag
                "\"PAY_FLAG\" INTEGER NOT NULL ," + // 25: payFlag
                "\"MASTER_RANK_ID\" TEXT," + // 26: masterRankId
                "\"MASTER_RANK_NAME\" TEXT);"); // 27: masterRankName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
 
        String userText = entity.getUserText();
        if (userText != null) {
            stmt.bindString(3, userText);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindLong(5, entity.getLockFlag());
 
        String invitationCode = entity.getInvitationCode();
        if (invitationCode != null) {
            stmt.bindString(6, invitationCode);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(7, token);
        }
 
        String role = entity.getRole();
        if (role != null) {
            stmt.bindString(8, role);
        }
 
        String registrationTime = entity.getRegistrationTime();
        if (registrationTime != null) {
            stmt.bindString(9, registrationTime);
        }
 
        String identityCard = entity.getIdentityCard();
        if (identityCard != null) {
            stmt.bindString(10, identityCard);
        }
 
        String validityDataStart = entity.getValidityDataStart();
        if (validityDataStart != null) {
            stmt.bindString(11, validityDataStart);
        }
 
        String validityDataEnd = entity.getValidityDataEnd();
        if (validityDataEnd != null) {
            stmt.bindString(12, validityDataEnd);
        }
 
        String idCardFrontPhoto = entity.getIdCardFrontPhoto();
        if (idCardFrontPhoto != null) {
            stmt.bindString(13, idCardFrontPhoto);
        }
 
        String idCardBackPhoto = entity.getIdCardBackPhoto();
        if (idCardBackPhoto != null) {
            stmt.bindString(14, idCardBackPhoto);
        }
 
        String validityData = entity.getValidityData();
        if (validityData != null) {
            stmt.bindString(15, validityData);
        }
 
        String bareHeadedPhotoUrl = entity.getBareHeadedPhotoUrl();
        if (bareHeadedPhotoUrl != null) {
            stmt.bindString(16, bareHeadedPhotoUrl);
        }
        stmt.bindLong(17, entity.getAuthenticationStatue());
 
        String authenticationTime = entity.getAuthenticationTime();
        if (authenticationTime != null) {
            stmt.bindString(18, authenticationTime);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(19, address);
        }
        stmt.bindDouble(20, entity.getLongitude());
        stmt.bindDouble(21, entity.getLatitude());
 
        String businessLicense = entity.getBusinessLicense();
        if (businessLicense != null) {
            stmt.bindString(22, businessLicense);
        }
 
        String emergencyContact = entity.getEmergencyContact();
        if (emergencyContact != null) {
            stmt.bindString(23, emergencyContact);
        }
 
        String contactAddress = entity.getContactAddress();
        if (contactAddress != null) {
            stmt.bindString(24, contactAddress);
        }
        stmt.bindLong(25, entity.getStartFlag());
        stmt.bindLong(26, entity.getPayFlag());
 
        String masterRankId = entity.getMasterRankId();
        if (masterRankId != null) {
            stmt.bindString(27, masterRankId);
        }
 
        String masterRankName = entity.getMasterRankName();
        if (masterRankName != null) {
            stmt.bindString(28, masterRankName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
 
        String userText = entity.getUserText();
        if (userText != null) {
            stmt.bindString(3, userText);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindLong(5, entity.getLockFlag());
 
        String invitationCode = entity.getInvitationCode();
        if (invitationCode != null) {
            stmt.bindString(6, invitationCode);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(7, token);
        }
 
        String role = entity.getRole();
        if (role != null) {
            stmt.bindString(8, role);
        }
 
        String registrationTime = entity.getRegistrationTime();
        if (registrationTime != null) {
            stmt.bindString(9, registrationTime);
        }
 
        String identityCard = entity.getIdentityCard();
        if (identityCard != null) {
            stmt.bindString(10, identityCard);
        }
 
        String validityDataStart = entity.getValidityDataStart();
        if (validityDataStart != null) {
            stmt.bindString(11, validityDataStart);
        }
 
        String validityDataEnd = entity.getValidityDataEnd();
        if (validityDataEnd != null) {
            stmt.bindString(12, validityDataEnd);
        }
 
        String idCardFrontPhoto = entity.getIdCardFrontPhoto();
        if (idCardFrontPhoto != null) {
            stmt.bindString(13, idCardFrontPhoto);
        }
 
        String idCardBackPhoto = entity.getIdCardBackPhoto();
        if (idCardBackPhoto != null) {
            stmt.bindString(14, idCardBackPhoto);
        }
 
        String validityData = entity.getValidityData();
        if (validityData != null) {
            stmt.bindString(15, validityData);
        }
 
        String bareHeadedPhotoUrl = entity.getBareHeadedPhotoUrl();
        if (bareHeadedPhotoUrl != null) {
            stmt.bindString(16, bareHeadedPhotoUrl);
        }
        stmt.bindLong(17, entity.getAuthenticationStatue());
 
        String authenticationTime = entity.getAuthenticationTime();
        if (authenticationTime != null) {
            stmt.bindString(18, authenticationTime);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(19, address);
        }
        stmt.bindDouble(20, entity.getLongitude());
        stmt.bindDouble(21, entity.getLatitude());
 
        String businessLicense = entity.getBusinessLicense();
        if (businessLicense != null) {
            stmt.bindString(22, businessLicense);
        }
 
        String emergencyContact = entity.getEmergencyContact();
        if (emergencyContact != null) {
            stmt.bindString(23, emergencyContact);
        }
 
        String contactAddress = entity.getContactAddress();
        if (contactAddress != null) {
            stmt.bindString(24, contactAddress);
        }
        stmt.bindLong(25, entity.getStartFlag());
        stmt.bindLong(26, entity.getPayFlag());
 
        String masterRankId = entity.getMasterRankId();
        if (masterRankId != null) {
            stmt.bindString(27, masterRankId);
        }
 
        String masterRankName = entity.getMasterRankName();
        if (masterRankName != null) {
            stmt.bindString(28, masterRankName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // phoneNumber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userText
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.getInt(offset + 4), // lockFlag
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // invitationCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // token
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // role
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // registrationTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // identityCard
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // validityDataStart
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // validityDataEnd
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // idCardFrontPhoto
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // idCardBackPhoto
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // validityData
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // bareHeadedPhotoUrl
            cursor.getInt(offset + 16), // authenticationStatue
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // authenticationTime
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // address
            cursor.getDouble(offset + 19), // longitude
            cursor.getDouble(offset + 20), // latitude
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // businessLicense
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // emergencyContact
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // contactAddress
            cursor.getInt(offset + 24), // startFlag
            cursor.getInt(offset + 25), // payFlag
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // masterRankId
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27) // masterRankName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhoneNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserText(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLockFlag(cursor.getInt(offset + 4));
        entity.setInvitationCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setToken(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRole(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRegistrationTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIdentityCard(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setValidityDataStart(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setValidityDataEnd(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setIdCardFrontPhoto(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setIdCardBackPhoto(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setValidityData(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setBareHeadedPhotoUrl(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setAuthenticationStatue(cursor.getInt(offset + 16));
        entity.setAuthenticationTime(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setAddress(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setLongitude(cursor.getDouble(offset + 19));
        entity.setLatitude(cursor.getDouble(offset + 20));
        entity.setBusinessLicense(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setEmergencyContact(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setContactAddress(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setStartFlag(cursor.getInt(offset + 24));
        entity.setPayFlag(cursor.getInt(offset + 25));
        entity.setMasterRankId(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setMasterRankName(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
