package fga.mds.gpp.trezentos.Model;


import java.io.Serializable;
import java.util.ArrayList;

import fga.mds.gpp.trezentos.Exception.UserException;

public class UserClass implements Serializable {

    private String className;
    private String institution;
    private float cutOff;
    private String password;
    private float addition;
    private int sizeGroups;

    public UserClass(){


    }

    public UserClass(String className, String institution, float cutOff, String password,
                     float addition, Integer sizeGroups) throws UserException{

        setClassName(className);
        setInstitution(institution);
        setCutOff(cutOff);
        setPassword(password);
        setAddition(addition);
        setSizeGroups(sizeGroups);
    }

    public String getClassName() {

        return className;
    }


    public void setClassName(String className) throws UserException {
        int MIN_CLASSNAME_LENGTH = 3;
        int MAX_CLASSNAME_LENGTH = 20;

        if((className != null && !className.isEmpty())
                &&(className.length() < MIN_CLASSNAME_LENGTH
                || className.length() > MAX_CLASSNAME_LENGTH)){

            throw new UserException("O nome da sala deve ter de 3 a 20 caracteres.");
        }else if(className == null || className.isEmpty()){
            throw new UserException("Preencha todos os campos!");

        }else {

            this.className = className;
        }
    }

    public int getSizeGroups() {
        return sizeGroups;
    }

    public void setSizeGroups(int sizeGroups) throws UserException {
        if(sizeGroups > 0){

            this.sizeGroups = sizeGroups;

        }else{

            throw new UserException("O tamanho do grupo nao pode ser zero.");
        }
    }


    public String getInstitution() {
        return institution;
    }


    public void setInstitution(String institution)throws UserException {

        int MIN_INSTITUTION_LENGTH = 3;
        int MAX_INSTITUTION_LENGTH = 20;

        if((institution != null && !institution.isEmpty()) &&
                (institution.length() < MIN_INSTITUTION_LENGTH
                        || institution.length() > MAX_INSTITUTION_LENGTH)){

            throw new UserException("O nome da instituicao deve ter de 3 a 20 caracteres.");

        }else{

            this.institution = institution;
        }

    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) throws UserException {

        int MIN_PASSWORD_LENGTH = 6;
        int MAX_PASSWORD_LENGTH = 16;

        if((password != null && !password.isEmpty()) &&
                (password.length() < MIN_PASSWORD_LENGTH ||
                        password.length() > MAX_PASSWORD_LENGTH)){

            throw new UserException("A senha deve ter entre 6 e 16 caracteres");

        }else if(password == null || password.isEmpty()){
            throw new UserException("Preencha todos os campos!");

        }else{

            this.password = password;
        }

    }

    public float getAddition(){
        return addition;
    }

    public void setAddition(float addition) throws UserException{

        if(addition != 0){

            this.addition = addition;

        }else{

            throw new UserException("O acrescimo nao pode ser zero.");
        }
    }


    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) throws UserException {

        if(cutOff != 0){

            this.cutOff = cutOff;

        }else{

            throw new UserException("A nota de corte nao pode ser zero.");
        }

    }


}