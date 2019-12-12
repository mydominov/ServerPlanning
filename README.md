  ____                                ____  _                   _             
 / ___|  ___ _ ____   _____ _ __     |  _ \| | __ _ _ __  _ __ (_)_ __   __ _ 
 \___ \ / _ \ '__\ \ / / _ \ '__|____| |_) | |/ _` | '_ \| '_ \| | '_ \ / _` |
  ___) |  __/ |   \ V /  __/ | |_____|  __/| | (_| | | | | | | | | | | | (_| |
 |____/ \___|_|    \_/ \___|_|       |_|   |_|\__,_|_| |_|_| |_|_|_| |_|\__, |
                                                                        |___/ 
------------------------------------------------------------------------------

The program that calculates the server hardware for a data center (to host virtual machines).

All servers are from the same type. This type defines the hardware resources each server provides: CPU, RAM, HDD.
Each virtual machine is defined by the virtual hardware resources it needs (on a server): CPU, RAM, HDD.

The algorithm for the virtual machine distribution should implement a 'first fit' strategy. 
This means there is no resource optimization or 'look back': Virtual machines are always allocated on the current or the next server (in case of limited resources). 

If a virtual machine is too 'big' for a new server, it is skipped.

------------------------------------------------------------------------------
Example:
    - Server type = {CPU: 2, RAM: 32, HDD: 100}
    - Virtual Machines = [{CPU: 1, RAM: 16, HDD: 10}, {CPU: 1, RAM: 16, HDD: 10}, {CPU: 2, RAM: 32, HDD: 100}]
    - Result = 2
