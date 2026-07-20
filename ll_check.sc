__config() -> {
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
