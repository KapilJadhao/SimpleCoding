package com.mtp.simplecoding;

import android.content.Context;
import android.content.SharedPreferences;

import static com.mtp.simplecoding.Utility.chknull;


public class SharedPreferenceHelper {

 private SharedPreferences sharedPreferences;

 public SharedPreferenceHelper(Context context, String name){
  sharedPreferences=context.getSharedPreferences(name, Context.MODE_PRIVATE);
 }

 public void putInt(@SuppressWarnings("rawtypes") Enum key, int value){
  sharedPreferences.edit().putString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(value)).trim()).commit();
 }

 public void putString(@SuppressWarnings("rawtypes") Enum key, String value){
  sharedPreferences.edit().putString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(chknull(value, "")).trim()).commit();
 }

 public void putString(String key, String value){
  sharedPreferences.edit().putString(CipherUtils.encrypt(key).trim(), CipherUtils.encrypt(chknull(value, "")).trim()).commit();
 }

 public void putFloat(@SuppressWarnings("rawtypes") Enum key, float value){
  sharedPreferences.edit().putString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(value)).trim()).commit();
 }

 public void putBoolean(@SuppressWarnings("rawtypes") Enum key, boolean value){
  sharedPreferences.edit().putString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(value)).trim()).commit();
 }

 public int getInt(@SuppressWarnings("rawtypes") Enum key, int value){
  return Integer.parseInt(CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(value)).trim())));
 }

 public String getString(@SuppressWarnings("rawtypes") Enum key, String value){
  return CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(value).trim()));
 }

 public String getString(String key, String value){
  return CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key).trim(), CipherUtils.encrypt(value).trim()));
 }

 public float getFloat(@SuppressWarnings("rawtypes") Enum key, float value){
  return Float.parseFloat(CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(value)).trim())));
 }

 public boolean getBoolean(@SuppressWarnings("rawtypes") Enum key, boolean value){
  return Boolean.parseBoolean(CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(value)).trim())));
 }

 public int getInt(@SuppressWarnings("rawtypes") Enum key){
  return Integer.parseInt(CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt("0").trim())));
 }

 public String getString(@SuppressWarnings("rawtypes") Enum key){
  return CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt("").trim()));
 }

 public float getFloat(@SuppressWarnings("rawtypes") Enum key){
  return Float.parseFloat(CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt("0.00").trim())));
 }

 public boolean getBoolean(@SuppressWarnings("rawtypes") Enum key){
  return Boolean.parseBoolean(CipherUtils.decrypt(sharedPreferences.getString(CipherUtils.encrypt(key.name()).trim(), CipherUtils.encrypt(String.valueOf(false)).trim())));
 }
}
