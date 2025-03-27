
#dksh language spec
##############################################################################
#
# While I'm going through lox I'm going to craft a temp language spec.
# IDK if i want classes or ; terminated lines
#
#


# Data Types
###############################

# This is a basic string data type. UTF-8 encoded
strings

# Following lox we will have a 
# bash just has int, which internally are a signed 


# If I can't get these to work on 32-bit hardware, well drop the max value to 32 bit eqvialants)
# https://stackoverflow.com/questions/32167925/how-does-a-32-bit-machine-compute-a-double-precision-number
# Mirroring zsh and yash
# double precision floating point (64bit - 
floats


# If I can't get these to work on 32-bit hardware, well drop the max value to 32 bit eqvialants)
# mirroring bash 64-bit integers which can hold at max 9223372036854775807
int

arrays

# return true or false
boolean 


# Expressions
###############################

# arithmetic
infix: +, -, *, /
prefix: - 
modules %

# comparion and equality 
< <= > >=

! as a prefix returns false if the operand is true and true if the operand is false
1 == 2           # false
"cat" != "dog"   # true

true and false; # false.
true and true;  # true.

false or false; # false.
true or false;  # true.

# Statements
###############################

# Variables
###############################
Varibles declared in the root of the shell script are global to the script.
All system environment variables are global variables accessable in the script
    E.g.
    PATH=/usr/bin:/usr/sbin 

    in your script you can call $PATH and agment it locally to your script or 
    export it globally with either by exporting the modifed varaible or directly

    E.g.
    export $PATH # After modifying it
    export PATH=/usr/bin:/usr/sbin:/dbin # direclty -- like in /etc/profile, .bashrc, .bash_profile, etc

var --> Declares a mutable variable
    var hello; -> sets it to nil
    var hello = "hello";

# bash has a built in of readonly. --> to make subsequant assignment in the script not possible
# well achive this with a const keyword
const hello = "hello";

    # This is not allowed when declared as a const.
    $hello = "goodby";

Access varibles with $
var hello = "Hello";

echo $hello 

# expansion
# https://stackoverflow.com/questions/68140682/how-to-use-parameter-expansion-correctly-in-bash
${} expand as a varible
$() execute as a command


# Control Flow
###############################

if (condition) {
  print "yes";
} else {
  print "no";
}

# This can be placed in the spot of else as well
# just like c
else if (condition) {}

var a = 1;
while ($a < 10) {
  print $a;
  $a = $a + 1;
}

switch ($var) {
    case one:
        # your code
        break;

    case two:
        #your code
        break;

    defualt:
        # have a defualt option
}

for (var a = 1; $a < 10; $a = $a + 1) {
  print $a;
}

# instead of doing $a = $a + 1 I'll add the ++ and -- operators
so you can just say $a++ to incriment by 1.

# Functions
###############################
functions are declared with fn keyword

# no main fun is required 
fn main() {

}

Functions are full class citizens and can be set as the resolve to varibels 
or pass as paramiters to other functions (lox)

fn addNum(a, b) {
    return $a + $b; 
}

fn isGreaterThan10(a)(10, 20){
    const num = a
    if ($a > 10) {
        echo "It's greater than 10";
        return true;
    } else {
        echo "It's not"
        return false; 
    } 
}

Parameters of the passed function go in () after the fn name(). 
These () are in order of functions passed.
echo isGreaterThan10(addNum)(10, 20);

If a function doesn't make an explicit return statement it returns nil (null)


# Classes (Not sold on yet)
###############################

# Scoop
###############################

# Core - Standard Library
###############################
# This feels like bash builtins to me

exit()      -> exits the program, optionally allows you to pass an exit code
input()     -> Gets a user intput. Optionally takes a message to display
pause()     -> waits for key press. Optionally takes a message to display
str()       -> Converts given value to string
int()       -> Converts given value to int
float()     -> Converts given value to float
ostype      -> When built, it builds in OS type from and returns it in scripts. 
               It can return (windows, linux, freebsd, macos)
               if (ostype == "linux") {
                // do things only for linux
               }

&&          -> run a second command if the first $? exited sucefully
|           -> pipe the out put of one command into the input of another
>           -> if used alone (ie. not in control flow) > means redirct and it overwrites any existing file
>>          -> if used alone (ie. not in control flow) >> means redirect and appends to any existing file

export      -> exports a varible as a system environment variable

command()   ->  I don't want to have to do this unless absolutly necessary.
                Though if need by you would pass in a executable in your path with its arguments 
                as a string. eg. command("ls --color") or hardcode path to executable command("/usr/bin/ls --color")
                I want this to be like bash were you just say ls and move on. 

local       -> Allows you to modify a global variable locally just for that function, class or method.
               scooping should eliminate the need for this

print       -> I may need an internal print command. -- Echo won't cut it. Maybe printf will. (hopefully won't need to hard code to gnu)

# If there is a good cross platform terminfo/cursers/termcap file, maybe implement basic terminal manupluation from 
# functions instead of relying on escape sequences like bash. -- Though maybe I'll leave it just like bash.