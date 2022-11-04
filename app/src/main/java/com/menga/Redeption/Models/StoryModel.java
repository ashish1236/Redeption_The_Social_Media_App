package com.menga.Redeption.Models;

import java.util.ArrayList;

public class StoryModel {
 public StoryModel() {
 }

 private   String Storyby;
  private long Storyat;
  private String storyid;

  ArrayList<UsersStories> stories;

 public String getStoryby() {
  return Storyby;
 }

 public void setStoryby(String storyby) {
  Storyby = storyby;
 }

 public long getStoryat() {
  return Storyat;
 }

 public void setStoryat(long storyat) {
  Storyat = storyat;
 }

 public ArrayList<UsersStories> getStories() {
  return stories;
 }

 public void setStories(ArrayList<UsersStories> stories) {
  this.stories = stories;
 }

 public String getStoryid() {
  return storyid;
 }

 public void setStoryid(String storyid) {
  this.storyid = storyid;
 }
}
