% -------------DOMAINS------------- %
time(0..6).
track(main(0..5);side(0..5)).
object(g1;g3;g1;g2).
group(g1;g3;g1;g2).
numberInGroup(g1,5;g3,1;g1,5;g2,5).
bridgeOn(b,main(3);a,main(0);b,side(3)).
buttonOn(main(0),side(1)).
buttonOn(main(0)).


posFluent(on(train,M)):-track(M).
posFluent(on(G,M)):-object(G),track(M).
posFluent(on(G,b)):-object(G).
posFluent(alive(G)):-group(G).
negative(neg(F)):-posFluent(F).
fluent(F):-posFluent(F).
fluent(F):-negative(F).
% -------------INITIAL SITUATION------------- %
initially(on(g1,side(2);g3,b;g1,main(2);g2,b)).
initially(alive(g1;g3;g1;g2)).
initially(on(train,main(0))).
% -------------SCENARIOS------------- %
sim(s0;s1).
performs(s0,push(g3,b),0).
% ------------ EVENT SPECIFICATION ------------------------------ %

% SWITCH
act(switch(M,N)):-buttonOn(M,N).
prec(on(train,M),switch(M,N)):-act(switch(M,N)).
effect(switch(M,N),neg(on(train,M))):-act(switch(M,N)).
effect(switch(M,N),on(train,N)):-act(switch(M,N)).

act(switch(M)):-buttonOn(M).
prec(on(train,M),switch(M)):-act(switch(M)).
effect(switch(M),neg(on(train,M))):-act(switch(M)).
effect(switch(main(N)),on(train,side(N))):-act(switch(main(N))).
effect(switch(side(N)),on(train,main(N))):-act(switch(side(N))).

% PUSH
act(push(O,B)):-object(O),bridgeOn(B,_),initially(on(O,B)).
prec(on(O,B),push(O,B)):-act(push(O,B)).
effect(push(O,B),neg(on(O,B))):-act(push(O,B)).
effect(push(O,B),on(O,M)):-act(push(O,B)), bridgeOn(B,M).

% Automatic action : run
auto(run(train,M)):-track(M).
prec(on(train,M),run(train,M)):-auto(run(train,M)).
effect(run(train,main(N-1)),on(train,main(N))):-auto(run(train,main(N))).
effect(run(train,side(N-1)),on(train,side(N))):-auto(run(train,side(N))).
effect(run(train,M),neg(on(train,M))):-auto(run(train,M)).

% Automatic action : crash
auto(crash(G,M)):-object(G),track(M).
prec(on(G,M),crash(G,M)):-auto(crash(G,M)).
prec(on(train,M),crash(G,M)):-auto(crash(G,M)).
effect(crash(G,M),neg(alive(G))):-auto(crash(G,M)).
effect(crash(G,M),neg(on(train,M))):-auto(crash(G,M)).

%Priorities
priority(crash(G,M),run(train,M)):-auto(crash(G,M)).
priority(A,run(train,M)):-act(A),track(M).


