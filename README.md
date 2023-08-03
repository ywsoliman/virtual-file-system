# Virtual File System
 
Assuming that we have a virtual file system with a root directory called **root** all the files and folders will be stored under it. The disk size consists of **N** blocks and each block size is **1 KB**.

Simulate the allocation and de-allocation of files and folders using different allocation techniques:

- Contiguous Allocation (Using Best Fit allocation)
- Indexed Allocation
- Linked Allocation

After running the application, the user will interact with your virtual file system through a series of commands, these commands are illustrated in the table below:

## System Commands:
| Command | Description | Pre-requests |
| ------- | ----------- | ------------ |
| CreateFile root/file.txt 100     | This command used to create file named “file.txt” with 100 KB size under the path “root”. | 1. The path already exists. <br> 2. No file with the same name is already created under this path. <br> 3. Enough space exists.|
| CreateFolder root/folder1        | This command is used to create a new folder named “folder1” under the path “root”. | 1. The path already exists. <br> 2. No folder with the same name is already created under this path.|
| DeleteFile root/folder1/file.txt | This command used to delete file named “file.txt” form the path "root/folder1". Any blocks allocated by this file should be de-allocated. | The file is already exist under the path specified. |
| DeleteFolder root/folder1        | This command used to delete folder named “folder1” form the path "root". All files and subdirectories of this folder will also be deleted. | The folder is already exist under the path specified. |
| DisplayDiskStatus                | This command used to display the status of your Driver the status should contain the following information: <br> 1. Empty space <br> 2. Allocated space <br> 3. Empty Blocks in the Disk <br> 4. Allocated Blocks in the Disk |
| DisplayDiskStructure             | This command will display the files and folders in your system file in a tree structure. |

In this program we are not creating actual files and folder, we will just simulate having a series of blocks and these blocks will be allocated to files when created and will be de-allocated when these files are deleted.

Your virtual file system information like (the files information, the folders information, the allocated blocks and so on) should be saved on a file on your hard disk to be able to load it the next time you run the application.

So when the application starts, the system should automatically load the disk structure form the Virtual File System file say named `c:\DiskStructure.vfs`. Then the user will start to enter commands which will be executed on the loaded data in memory, and before the application terminates, the data in memory will be written into the file again.

The following information should be stored in the file:
1. Files and Folders Directory Structure.
2. The Empty blocks of the virtual DISK
3. The allocated blocks in your virtual DISK and which files/folders are take these places. 
