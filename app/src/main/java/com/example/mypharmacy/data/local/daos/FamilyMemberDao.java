package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mypharmacy.data.local.entities.FamilyMember;

import java.util.List;

@Dao
public interface FamilyMemberDao {
    @Insert
    void insert(FamilyMember familyMember);

    @Update
    void update(FamilyMember familyMember);

    @Delete
    void delete(FamilyMember familyMember);

    @Query("SELECT * FROM family_member WHERE family_id = :familyId")
    List<FamilyMember> getAllFamilyMembersByFamilyId(int familyId);

    @Query("SELECT * FROM family_member WHERE id = :familyMemberId")
    FamilyMember getFamilyMemberById(int familyMemberId);
}
