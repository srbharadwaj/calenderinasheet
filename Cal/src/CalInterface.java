/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class Name - CalInterface
 * Description - 
 *
 * @author Suhas Bharadwaj
 */
public interface CalInterface
{
    /**
     * Application Version
     * value must be changed in case version is changed
     */
    //1.2 - changed the code path in about box
    //    - changed UI a bit.
    static final String VERSION = "v1.2";

     /**
     * Application Name
     */
    static final String APP_NAME = "Cal-In-A-Sheet";

    /**
     * Application Title, which is Application Name plus Application Version
     */
    static final String TITLE = APP_NAME+" "+VERSION;

    /**
     * Application's Homepage
     */
    //1.2 - changed from google code to github
    //static final String HOMEPAGE = "https://code.google.com/p/calenderinasheet/";
    static final String HOMEPAGE = "https://github.com/srbharadwaj/calenderinasheet/";

    /**
     * Author's Homepage
     */
    static final String AUTH_HOMEPAGE = "https://srbharadwaj.wordpress.com/";

    /**
     * Application's Author name and Email id
     */
    static final String AUTHORandEMAILID = "Suhas Bharadwaj (srbharadwaj@gmail.com)";


}
