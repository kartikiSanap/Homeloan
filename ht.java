AffinityLock al = AffinityLock.acquireLock();
try {
    // find a cpu on a different socket, otherwise a different core.
    AffinityLock readerLock = al.acquireLock(DIFFERENT_SOCKET, DIFFERENT_CORE);
    new Thread(new SleepRunnable(readerLock, false), "reader").start();

    // find a cpu on the same core, or the same socket, or any free cpu.
    AffinityLock writerLock = readerLock.acquireLock(SAME_CORE, SAME_SOCKET, ANY);
    new Thread(new SleepRunnable(writerLock, false), "writer").start();

    Thread.sleep(200);
} finally {
    al.release();
}

// allocate a whole core to the engine so it doesn't have to compete for resources.
al = AffinityLock.acquireCore(false);
new Thread(new SleepRunnable(al, true), "engine").start();

Thread.sleep(200);
System.out.println("\nThe assignment of CPUs is\n" + AffinityLock.dumpLocks());