1. Write Java wrapper NativeCallsClass

2. Compile NativeCallsClass
`javac -d com\dandeeee\jnidemo\NativeCallsClass.java`

2. call javah for generating c++ headers for native methods called in NativeCallsClass from bin directory
`javah com.dandeeee.jnidemo.NativeCallsClass`

3. Write cpp with methods bodies

4. Build shared lib (on Mac)
`c++ -fPIC -o ../lib/libmegalib.dylib -shared -I/Volumes/root/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/include -I/Volumes/root/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/include/darwin megalib.cpp -v`

5. Add lib folder into path

6. profit