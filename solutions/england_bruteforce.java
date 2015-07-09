    public static void countCombosBruteForce() {
        int numWays = 0;
        for ( int twopounds = 0; twopounds <= 1; twopounds++ )
            for ( int pounds = 0; pounds <= 2; pounds++ )
                for ( int fiftyp = 0; fiftyp <= 4; fiftyp++ )
                    for ( int twentyp = 0; twentyp <= 10; twentyp++ )
                        for ( int tenp = 0; tenp <= 20; tenp++ )
                            for ( int fivep = 0; fivep <= 40; fivep++ )
                                for ( int twop = 0; twop <= 100; twop++ )
                                    for ( int onep = 0; onep <= 200; onep++ )
                                        if ( twopounds * 200 + pounds * 100 + fiftyp * 50 +
                                                twentyp * 20 + tenp * 10 + fivep * 5 +
                                                twop * 2 + onep == 200 ) {
                                            numWays++;
                                        }
        System.out.println("Ways "+numWays);
    }

