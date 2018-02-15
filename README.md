## Prereqisites

```
sudo yum install time perf sysstat atop strace
```

### Example 01, FileSlow

The program writes to a file lines up to N. N is passed via cmd line parameter:

`$ java -cp . FileSlow <N>`

Another option - pass via make file:

`$ ARGS=50000 make ex01`

#### Running an app
`$ ARGS=50000 make ex01`
#### Check how it's going
`$ htop`
#### See details on CPU, IO utilization
`$ vmstat 1`
#### Trace syscalls
* With `strace`, full log of syscalls `$ ARGS=500 strace -f make ex01`
* With `strace`, aggregated syscalls `$ ARGS=500 strace -f -c make ex01`   
* With `perf`, aggregated per call `$ ARGS=50000 perf stat -e 'syscalls:sys_enter_*'  make ex01`

#### Check after fix

`$ ARGS=50000 perf stat -e 'syscalls:sys_enter_*'  make ex01-fast`