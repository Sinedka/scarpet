watch_pos = null;
last_block = null;


__config() -> {
    'commands' -> {
        'start <origin_pos>' -> 'll_check',
    },
    'scope' -> 'player'
};


ll_check(pos) ->
(
    watch_pos = pos;
    last_block = block(watch_pos);
    print('Watching ' + watch_pos);
);

__on_tick() ->
(
    if(!watch_pos, exit());

    current = block(watch_pos);
    //print(current);

    if(
        last_block != 'minecraft:nether_portal'
        &&
        current == 'minecraft:nether_portal',

        tnts = entity_area('tnt', watch_pos, 128);

        if(length(tnts) > 0,
            nearest = min(tnts, distance(query(_, 'pos'), watch_pos));

            fuse = query(nearest, 'nbt', 'Fuse');

            print(format('Nearest TNT fuse: %d', fuse));
        ,
            print('No TNT found');
        );
    );

    last_block = current;
);
