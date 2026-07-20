__config() -> {
    'scope' -> 'global'
};

global('ll_targets', {});
global('ll_old_blocks', {});


__command('ll_check', 'll_check(x,y,z)', _(
    x = _;
    y = _;
    z = _;

    key = str(x)+' '+str(y)+' '+str(z);

    global('ll_targets') = global('ll_targets') + {
        key -> [x,y,z]
    };

    global('ll_old_blocks') = global('ll_old_blocks') + {
        key -> block(x,y,z)
    };

    print('§aWatching '+key);
));


__on_tick() -> (
    foreach(global('ll_targets'), key, pos,

        old = global('ll_old_blocks'):key;
        now = block(pos:0,pos:1,pos:2);

        if(
            old != 'minecraft:nether_portal'
            && now == 'minecraft:nether_portal',

            tnts = entity_list(
                'minecraft:tnt',
                pos:0-64,pos:1-64,pos:2-64,
                pos:0+64,pos:1+64,pos:2+64
            );

            if(length(tnts),
                fuse = nbt(tnts:0):'Fuse';

                print(
                    '§dPortal created! TNT fuse = '+str(fuse)
                );
            );
        );

        global('ll_old_blocks') = global('ll_old_blocks') + {
            key -> now
        };
    );
);__config() -> {
    'scope' -> 'global'
};

global('ll_targets', []);

__command('ll_check', 'xyz', 'll_check') -> (
    x = _,
    y = _,
    z = _;

    global('ll_targets') = global('ll_targets') + [[x,y,z];

    print('§aTracking portal at ' + x + ' ' + y + ' ' + z);
);

__on_tick() -> (
    foreach(global('ll_targets'), pos,
        x = pos:0;
        y = pos:1;
        z = pos:2;

        block = block(x,y,z);

        // портал появился в этот тик
        if(block == 'minecraft:nether_portal',
            tnts = find_entities(
                'minecraft:tnt',
                x-64, y-64, z-64,
                x+64, y+64, z+64
            );

            if(length(tnts) > 0,
                nearest = sort(tnts,
                    distance(pos, entity_pos(_))
                ):0;

                fuse = query(
                    nearest,
                    'Fuse'
                );

                print(
                    '§dPortal detected! TNT fuse: ' + fuse
                );
            ,
                print('§cPortal detected, no TNT nearby');
            );
        );
    );
);
