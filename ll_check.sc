global_watch_pos = null;
global_last_block = null;


__config() -> {
    'commands' -> {
        'start <origin_pos>' -> 'll_check',
    },
    'scope' -> 'player'
};


ll_check(pos) ->
(
    global_watch_pos = pos;
    global_last_block = block(pos);
    print('Watching ' + pos);
);

distance(pos1, pos2) -> (
    dx = abs(pos2:0 - pos1:0);
    dy = abs(pos2:1 - pos1:1);
    dz = abs(pos2:2 - pos1:2);

    sqrt(dx^2 + dy^2 + dz^2)
);

__on_tick() ->
(
    if(global_watch_pos == null,
      (
        exit();
      )
    );


    
    // global_last_block = block(pos);

    near = entity_area('tnt', global_watch_pos:0,global_watch_pos:1,global_watch_pos:2, 16, 16, 16);
    nearest = sort_key(
      near,
      distance(query(_, 'pos'), global_watch_pos)
    ):0;
    // if(nearest != null,
    //   (
    //     print(query(nearest, 'pos'));
    //     print(query(nearest, 'nbt', 'fuse'));
    //   ),
    //   print('none')
    // );
    if(nearest != null,
      fuse = query(nearest, 'nbt', 'fuse');
      if(fuse == 1,
          schedule(1, 'fun');
          schedule(2, 'fun1');
      )
    )

);

fun() ->
(
  if(block(global_watch_pos)!='nether_portal',
      print('not ok 1');
  );
  return(0);
);

fun1() ->
(
  if(block(global_watch_pos) !='air',
    print(block(global_watch_pos));
  );

  return(0);
);

