/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author d.gorshenin
 */
public interface ITreeElement {
    
    final ITreeElement MAIN_TREE = new ITreeElement() {
        @Override
        public List<ITreeElement> getTreeElements() {
            return new ArrayList<ITreeElement>(Realm.getMap().values());
        }
        
        private TreeSign treeSign = new TreeSign() {
            {
                setName("Дерево сущностей");
                setId("MAIN_TREE");
                setTableBgcolor("#E06017");
                setTdBgcolor("#FBDECE");
            }
                
            };

        @Override
        public TreeSign getTreeSign() {
            return  treeSign;
        }

    };
    
    ITreeElement LEARNING_TREE = new ITreeElement() {
        @Override
        public List<ITreeElement> getTreeElements() {
            return new ArrayList<ITreeElement>(Realm.getMap().values());
        }
        
        private TreeSign treeSign = new TreeSign() {
            {
                setName("Выберите раздел или тему для обучения");
                setId("LT");
                setTableBgcolor("#E06017");
                setTdBgcolor("#FBDECE");
            }
                
            };

        @Override
        public TreeSign getTreeSign() {
            return  treeSign;
        }

    };

    List<ITreeElement> getTreeElements();
    
    TreeSign getTreeSign();
    
    default String getTreeHTML(Map<String, Object> addInfo) {
        StringBuilder sb = new StringBuilder();
        TreeSign ts = getTreeSign();
        if (addInfo.get("expand") != null &&  addInfo.get("expand").equals(ts.getId()) )
            ts.setExpanded(true);
        if (addInfo.get("collapse") != null &&  addInfo.get("collapse").equals(ts.getId()) )
            ts.setExpanded(false);
        boolean isRoot;
        if (addInfo.get("isRoot") != null) {
            isRoot = (Boolean)addInfo.get("isRoot");
        } else {
            isRoot = false;
        }
        addInfo.put("isRoot", false);
        
        sb.append("<tr>\n");
        if (!isRoot)
            sb.append("<td id=\"" + ts.getId() + "\" width=\"1\" bgcolor=\"" + ts.getTableBgcolor() + "\" background=\"" + ts.getTabCellBackground() + "\">&nbsp;&nbsp;&nbsp;</td>\n");
        sb.append("<td bgcolor=\"" + ts.getTdBgcolor() + "\">\n");
        sb.append(ts.getName());
        sb.append("</td>\n");
        sb.append("<td width=\"1\" bgcolor=\"" + ts.getTdBgcolor() + "\">\n");
        sb.append("<a href=\"" + ts.getEditLink() + "\"><img width=\"20\" height=\"20\" src=\"images/" + (ts.getEditLink().equals("#") ? "edit_disabled.png" : "edit.png") + "\"></a>");
        sb.append("</td>\n");
        sb.append("<td width=\"1\" bgcolor=\"" + ts.getTdBgcolor() + "\">\n");
        sb.append("<a href=\"" + ts.getProfileLink() + "\"><img width=\"20\" height=\"20\" src=\"images/" + (ts.getProfileLink().equals("#") ? "profile_disabled.png" : "profile.png") + "\"></a>");
        sb.append("</td>\n");
        sb.append("<td width=\"1\" bgcolor=\"" + ts.getTdBgcolor() + "\">\n");
        if (ts.isExpanded()) {
            sb.append("<a href=\"view?info=tree&collapse=" + ts.getId() + "#" + ts.getId() + "\"><img width=\"20\" height=\"20\" src=\"images/collapse.png\"></a>");
        } else {
            sb.append("<a href=\"view?info=tree&expand=" + ts.getId() + "#" + ts.getId() + "\"><img width=\"20\" height=\"20\" src=\"images/expand.png\"></a>");
        }
        sb.append("</td>\n");
            
        sb.append("</tr>\n");



        if (ts.isExpanded()) {
            sb.append("<tr>\n");
            sb.append("<td bgcolor=\"" + ts.getTdBgcolor() + "\" colspan=\"" + (isRoot ? 4 : 5) + "\">\n");
            sb.append("<table width=\"100%\" cellpadding=\"10\" cellspacing=\"2\">\n");
            for (ITreeElement element: getTreeElements()) {
                sb.append(element.getTreeHTML(addInfo));
            }
            sb.append("</table>\n");
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }
        
        if (isRoot) {
            return "<table cellpadding=\"10\" cellspacing=\"1\" bgcolor=\"" + ts.getTableBgcolor() + "\">" + 
                    sb.toString() + "</table>";
        }

        return sb.toString();
    }
    
    default String getTreeHTML_Learning(Map<String, Object> addInfo) {
        StringBuilder sb = new StringBuilder();
        TreeSign ts = getTreeSign();
        if (addInfo.get("expand") != null &&  addInfo.get("expand").equals(ts.getId()) )
            ts.setExpanded(true);
        if (addInfo.get("collapse") != null &&  addInfo.get("collapse").equals(ts.getId()) )
            ts.setExpanded(false);
        boolean isRoot;
        if (addInfo.get("isRoot") != null) {
            isRoot = (Boolean)addInfo.get("isRoot");
        } else {
            isRoot = false;
        }
        addInfo.put("isRoot", false);
        String lastBranch = (String)addInfo.get("lastBranch");
        

        sb.append("<tr>\n");
        sb.append("<td bgcolor=\"" + ts.getTdBgcolor() + "\">\n");
        if (isRoot)
            sb.append(ts.getName());
        else
            sb.append("<a href=\"" + addInfo.get("url") + "?info=tree&details=" + ts.getId() + "\">" + ts.getName() + "</a>");
        sb.append("</td>\n");
        if (lastBranch == null || !ts.getId().startsWith(lastBranch)) { //это не крайняя ветвь, ее надо разворачивать
            if (addInfo.get("expandAll") != null && addInfo.get("expandAll").equals(Boolean.TRUE))
                ts.setExpanded(true);
            sb.append("<td width=\"1\" bgcolor=\"" + ts.getTdBgcolor() + "\">\n");
            if (ts.isExpanded()) {
                sb.append("<a href=\"" + addInfo.get("url") + "?info=tree&collapse=" + ts.getId() + "#" + ts.getId() + "\"><img width=\"20\" height=\"20\" src=\"images/collapse.png\"></a>");
            } else {
                sb.append("<a href=\"" + addInfo.get("url") + "?info=tree&expand=" + ts.getId() + "#" + ts.getId() + "\"><img width=\"20\" height=\"20\" src=\"images/expand.png\"></a>");
            }
            sb.append("</td>\n");
        }
        sb.append("</tr>\n");


        if (ts.isExpanded()) {
            sb.append("<tr>\n");
            sb.append("<td bgcolor=\"" + ts.getTdBgcolor() + "\" colspan=\"" + (isRoot ? 4 : 5) + "\">\n");
            sb.append("<table width=\"100%\" cellpadding=\"10\" cellspacing=\"2\">\n");
            for (ITreeElement element: getTreeElements()) {
                sb.append(element.getTreeHTML_Learning(addInfo));
            }
            sb.append("</table>\n");
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }
        
                
        if (isRoot) {
            //сбрасываем флаг expandAll
            addInfo.remove("expandAll");
            return "<table cellpadding=\"10\" cellspacing=\"1\" bgcolor=\"" + ts.getTableBgcolor() + "\">" + 
                    sb.toString() + "</table>";
        }

        return sb.toString();
    }
}
