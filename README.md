# TinMan2016
The year the code works

###Configuring workspace
To use this code in eclipse, make sure you have the c++ version of eclipse installed with the wpilib toolchain installed.  The instructions for this can be found on screestepslive.  

To start, create a eclipse workspce, or use a current one.  Create a new Java Robot project.  It is easiest to use the "simple" preset, becuase there are fewer files to delete.  Delete the src folder.  Add a new src folder to the project, but link it to the src folder in the git repository.  Then right click the project and add a new source folder, pointing to the src folder.

open `build.xml` in the eclipse project, and replace all of the text with the text in `build.xml` found in the root of the git repo.  Replace "PATH_TO_REPO" with the relative or absolute path to the root of the repo.


###Deploying without Running Eclipse
Sometimes it might be useful to deploy without having to open eclipse.  Eclipse must still be installed, or else ant would not work, however loading times might be invonvenient.
Simply run `Deploy.bat` or `ReUploadCfg.bat`
