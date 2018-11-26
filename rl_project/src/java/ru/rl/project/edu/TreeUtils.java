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
public class TreeUtils {
    
    public static ITreeElement getById(String complexId) {
        String[] type_and_id = complexId.split("_");
        switch (type_and_id[0]) {
            case "Realm":
                return Realm.getById(type_and_id[1]);
            case "Theme":
                return Theme.getById(type_and_id[1]);

        }
        
        return null;
    }
    
}
