/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

/**
 *
 * @author d.gorshenin
 */
public class TreeSign {
    private String editLink = "#";
    private String profileLink = "#";
    private String name;
    private boolean expanded;
    private String id;
    private String tableBgcolor = "#FFFFFF";
    private String tdBgcolor = "#FFFFFF";
    private String tabCellBackground = "#";

    public String getTabCellBackground() {
        return tabCellBackground;
    }

    public void setTabCellBackground(String tabCellBackground) {
        this.tabCellBackground = tabCellBackground;
    }

    public String getTableBgcolor() {
        return tableBgcolor;
    }

    public void setTableBgcolor(String tableBgcolor) {
        this.tableBgcolor = tableBgcolor;
    }

    public String getTdBgcolor() {
        return tdBgcolor;
    }

    public void setTdBgcolor(String tdBgcolor) {
        this.tdBgcolor = tdBgcolor;
    }




    public String getId() {
        return id;
    }
    

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getEditLink() {
        return editLink;
    }

    public void setEditLink(String editLink) {
        this.editLink = editLink;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setExpanded(boolean isExpanded) {
        this.expanded = isExpanded;
    }
    
}
