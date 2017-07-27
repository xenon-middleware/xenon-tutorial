```bash
# check if xenon cli can be found and works (should print a line about usage)
xenon

# get some help
# FIXME adaptor documentation some longer lines are wrapped incorrectly
# FIXME sort adaptor first by type, then by adaptorname
xenon --help

# check the version (should return 2.x.x)
xenon --version

# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

# usage line suggests I need to pick at least one from {file,ftp,gridengine,local,sftp,slurm,ssh,torque,webdav}
# not sure what ot do yet, let's keep it simple and ask for help on (local) file manipulation
# FIXME add longer --help alternative to existing -h option for 'xenon file' command (functionality seems to exist, but not included in usage line)
xenon file --help

# 'xenon file' usage line seems to suggest that I need to pick one from {copy,list,remove}. simplest one is probably 'list', so:
xenon file list --help
# so I need a 'path' as final argument
# TODO add examples of valid paths
# FIXME 'path of location' what does that mean (update: location refers to the --location LOCATION you get when doing 'xenon file --help')
# FIXME ls -1 gives 14 lines of results, xenon 15 for the same dir. It looks like xenon-cli adds an empty line
# FIXME what determines the order of printing
# valid syntax, absolute paths
xenon file list .
xenon file list $PWD
xenon file list $HOME
xenon file list /tmp
xenon file list /tmp/
# valid, but returns error because env var is empty
xenon file list $NON_EXISTENT_ENV_VAR

# valid syntax, relative paths
# FIXME error states the name of the dir, but not that it doesn't exist. maybe add that? 
xenon file list tmp
xenon file list build
xenon file list ./build
xenon file list build/install
xenon file list build/install/..

# I have a /tmp and a /home/daisycutter/tmp
cd /home/daisycutter
xenon file --location / list tmp
gives me the contents of /home/daisycutter/tmp (I expected contents of /tmp; code works correct as long as you interpret location as filesytemroot, see below)

# now I'm really trying to break it...
xenon file --location /home/daisycutter list tmp
# gives a useful error. all good. but we should maybe change the argument name 'location'...to fsroot/filesystemroot maybe?

# this works, but meaning of empty string as file system root is not very clear
xenon file --location '' list tmp

# TODO xenon file --location doesn't do much on linux (one fs root); try on windows

# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

# let's try to copy a file, first make it
cd /home/daisycutter/tmp
touch testfile.txt

# check the help
xenon file --help
# so need 'copy' argument
xenon file copy --help
# TODO location is not mentioned at this level (it is mentioned one level higher at xenon file --help)

# first try without any optional arguments
xenon file copy testfile.txt textfile.copy.txt
# (doesnt work as expected, possibly because xenon expects absolute paths (FIXME not mentioned in the usage)
xenon file copy /home/daisycutter/tmp/testfile.txt testfile.copy.txt
# fails in the same way
xenon file copy /home/daisycutter/tmp/testfile.txt /home/daisycutter/tmp/testfile.copy.txt
# copies, but hangs! because file is size zero perhaps?
dd if=/dev/urandom of=testfile1024.txt bs=1024 count=1
xenon file copy /home/daisycutter/tmp/testfile1024.txt /home/daisycutter/tmp/testfile1024.copy.txt
# nope, same behavior.

```
